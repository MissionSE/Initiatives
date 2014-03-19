package com.missionse.graphicplugin;

import gov.nasa.worldwind.*;
import gov.nasa.worldwind.event.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.pick.PickedObject;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.render.markers.*;
import gov.nasa.worldwind.terrain.SectorGeometryList;
import gov.nasa.worldwind.util.Logging;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class SurfaceCircleEditor implements SelectListener
{
	protected static final int NONE = 0;
	protected static final int MOVING = 1;
	protected static final int SIZING = 2;

	protected WorldWindow wwd;
	protected SurfaceCircle shape;
	protected MarkerLayer controlPointLayer;
	protected boolean armed;

	protected boolean active;
	protected int activeOperation = NONE;
	protected Position previousPosition = null;

	protected static class ControlPointMarker extends BasicMarker
	{
		private int index;

		public ControlPointMarker(Position position, MarkerAttributes attrs, int index)
		{
			super(position, attrs);
			this.index = index;
		}

		public int getIndex()
		{
			return this.index;
		}
	}

	public SurfaceCircleEditor(WorldWindow wwd, SurfaceCircle shape)
	{
		if (wwd == null)
		{
			String msg = Logging.getMessage("nullValue.WorldWindow");
			Logging.logger().log(java.util.logging.Level.FINE, msg);
			throw new IllegalArgumentException(msg);
		}
		if (shape == null)
		{
			String msg = Logging.getMessage("nullValue.Shape");
			Logging.logger().log(java.util.logging.Level.FINE, msg);
			throw new IllegalArgumentException(msg);
		}

		this.wwd = wwd;
		this.shape = shape;
		this.controlPointLayer = new MarkerLayer();
	}

	public WorldWindow getWwd()
	{
		return this.wwd;
	}

	public SurfaceCircle getSurfaceImage()
	{
		return this.shape;
	}

	public boolean isArmed()
	{
		return this.armed;
	}

	public void setArmed(boolean armed)
	{
		if (!this.armed && armed)
		{
			this.enable();
		}
		else if (this.armed && !armed)
		{
			this.disable();
		}

		this.armed = armed;
	}

	protected void enable()
	{
		LayerList layers = this.wwd.getModel().getLayers();

		if (!layers.contains(this.controlPointLayer))
			layers.add(this.controlPointLayer);

		if (!this.controlPointLayer.isEnabled())
			this.controlPointLayer.setEnabled(true);

		this.updateAffordances();
		this.active = true;
		this.wwd.addSelectListener(this);
	}

	protected void disable()
	{
		LayerList layers = this.wwd.getModel().getLayers();
		layers.remove(this.controlPointLayer);
		wwd.removeSelectListener(this);
	}

	public void selected(SelectEvent event)
	{
		if (event == null)
		{
			String msg = Logging.getMessage("nullValue.EventIsNull");
			Logging.logger().log(java.util.logging.Level.FINE, msg);
			throw new IllegalArgumentException(msg);
		}

		if (event.getTopObject() != null
				&& !(event.getTopObject() == this.shape || event.getTopPickedObject().getParentLayer() == this.controlPointLayer))
		{
			((Component) this.wwd).setCursor(null);
			return;
		}

		if (event.getEventAction().equals(SelectEvent.DRAG_END))
		{
			this.active = false;
			this.activeOperation = NONE;
			this.previousPosition = null;
		}
		else if (event.getEventAction().equals(SelectEvent.ROLLOVER))
		{
			if (!(this.wwd instanceof Component))
				return;

			if (event.getTopObject() == null || event.getTopPickedObject().isTerrain())
			{
				((Component) this.wwd).setCursor(null);
				return;
			}

			Cursor cursor;
			if (event.getTopObject() instanceof SurfaceCircle)
				cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			else if (event.getTopObject() instanceof Marker)
				cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
			else
				cursor = null;

			((Component) this.wwd).setCursor(cursor);
		}
		else if (event.getEventAction().equals(SelectEvent.LEFT_PRESS))
		{
			this.active = true;
			this.previousPosition = this.wwd.getCurrentPosition();
		}
		else if (event.getEventAction().equals(SelectEvent.DRAG))
		{
			if (!this.active)
				return;
			DragSelectEvent dragEvent = (DragSelectEvent) event;
			Object topObject = dragEvent.getTopObject();
			if (topObject == null)
				return;

			if (topObject == this.shape || this.activeOperation == MOVING)
			{
				this.activeOperation = MOVING;
				this.dragWholeShape(dragEvent, topObject);
				this.updateAffordances();
				event.consume();
			}
			else if (dragEvent.getTopPickedObject().getParentLayer() == this.controlPointLayer
					|| this.activeOperation == SIZING)
			{
				this.activeOperation = SIZING;
				this.resizeShape(topObject);
				this.updateAffordances();
				event.consume();
			}
		}
	}

	protected void dragWholeShape(DragSelectEvent dragEvent, Object topObject)
	{
		if (!(topObject instanceof Movable))
			return;

		Movable dragObject = (Movable) topObject;

		View view = wwd.getView();
		Globe globe = wwd.getModel().getGlobe();

		Position refPos = dragObject.getReferencePosition();
		if (refPos == null)
			return;

		double refElevation = this.computeSurfaceElevation(wwd, refPos);
		refPos = new Position(refPos, refElevation);
		Vec4 refPoint = globe.computePointFromPosition(refPos);
		Vec4 screenRefPoint = view.project(refPoint);

		int dx = dragEvent.getPickPoint().x - dragEvent.getPreviousPickPoint().x;
		int dy = dragEvent.getPickPoint().y - dragEvent.getPreviousPickPoint().y;

		double x = screenRefPoint.x + dx;
		double y = dragEvent.getMouseEvent().getComponent().getSize().height - screenRefPoint.y + dy - 1;
		gov.nasa.worldwind.geom.Line ray = view.computeRayFromScreenPoint(x, y);
		Intersection inters[] = globe.intersect(ray, refPos.getElevation());

		if (inters != null)
		{
			Position p = globe.computePositionFromPoint(inters[0].getIntersectionPoint());
			dragObject.moveTo(p);
		}
	}

	protected void resizeShape(Object topObject)
	{
		if (!(topObject instanceof ControlPointMarker))
			return;

		PickedObject terrainObject = this.wwd.getObjectsAtCurrentPosition().getTerrainObject();
		if (terrainObject == null)
			return;

		Position p = terrainObject.getPosition();
		this.previousPosition = p;

		LatLon location1 = this.shape.getCenter();
		LatLon location2 = p;
		double altitude1 = 0;
		double altitude2 = 0;

		Vec4 p1 = this.getSurfacePoint(location1, altitude1);
		Vec4 p2 = this.getSurfacePoint(location2, altitude2);

		double d = p1.distanceTo3(p2);
		this.shape.setRadius(d);
		this.getWwd().redraw();
	}

	protected Vec4 getSurfacePoint(LatLon latlon, double elevation)
	{
		Vec4 point = null;

		SceneController sc = this.getWwd().getSceneController();
		Globe globe = this.getWwd().getModel().getGlobe();

		if (sc.getTerrain() != null)
		{
			point = sc.getTerrain().getSurfacePoint(latlon.getLatitude(), latlon.getLongitude(),
					elevation * sc.getVerticalExaggeration());
		}

		if (point == null)
		{
			double e = globe.getElevation(latlon.getLatitude(), latlon.getLongitude());
			point = globe.computePointFromPosition(latlon.getLatitude(), latlon.getLongitude(),
					(e + elevation) * sc.getVerticalExaggeration());
		}

		return point;
	}

	protected void updateAffordances()
	{
		java.util.List<LatLon> corners = new ArrayList<LatLon>(
				(Collection<? extends LatLon>) this.shape.getLocations(this.wwd.getView().getGlobe()));

		double d = LatLon.getAverageDistance(corners).radians * wwd.getModel().getGlobe().getRadius();

		MarkerAttributes markerAttrs = new BasicMarkerAttributes(Material.BLUE, BasicMarkerShape.SPHERE, 0.7, 10, 0.1,
				d / 30);

		ArrayList<LatLon> handlePositions = new ArrayList<LatLon>(8);
		for (LatLon corner : corners)
		{
			handlePositions.add(corner);
		}

		ArrayList<Marker> handles = new ArrayList<Marker>(handlePositions.size());
		for (int i = 0; i < handlePositions.size(); i++)
		{
			handles.add(new ControlPointMarker(new Position(handlePositions.get(i), 0), markerAttrs, i));
		}

		this.controlPointLayer.setOverrideMarkerElevation(true);
		this.controlPointLayer.setElevation(0);
		this.controlPointLayer.setKeepSeparated(false);
		this.controlPointLayer.setMarkers(handles);
	}

	protected double computeSurfaceElevation(WorldWindow wwd, LatLon latLon)
	{
		SectorGeometryList sgl = wwd.getSceneController().getTerrain();
		if (sgl != null)
		{
			Vec4 point = sgl.getSurfacePoint(latLon.getLatitude(), latLon.getLongitude(), 0.0);
			if (point != null)
			{
				Position pos = wwd.getModel().getGlobe().computePositionFromPoint(point);
				return pos.getElevation();
			}
		}

		return wwd.getModel().getGlobe().getElevation(latLon.getLatitude(), latLon.getLongitude());
	}
}
