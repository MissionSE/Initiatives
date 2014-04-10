package setup.com.solipsys.tdf.application.main.TacSit;

import com.solipsys.alerts.gui.RecentAlertPanel;
import com.solipsys.application.Application;
import com.solipsys.application.event.ApplicationEvent;
import com.solipsys.application.event.ApplicationListenerAdapter;
import com.solipsys.chat.gui.RecentChatPanel;
import com.solipsys.context.ContextConstants;
import com.solipsys.gui.ButtonScrollPane;
import com.solipsys.gui.SSplitPane;
import com.solipsys.gui.dashboard.Dashboard;
import com.solipsys.gui.dashboard.DashboardUtil;
import com.solipsys.gui.dashboard.DefaultDashboard;
import com.solipsys.gui.layout.GridBagLayout;
import com.solipsys.gui.layout.ListLayout;
import com.solipsys.gui.layout.OrientationLayout;
import com.solipsys.gui.toolbar.DockManager;
import com.solipsys.gui.toolbar.DockManagerGroup;
import com.solipsys.gui.toolbar.InstanceContext;
import com.solipsys.gui.toolbar.ToolBarGroup;
import com.solipsys.model.ModelEditor;
import com.solipsys.model.ModelEditorFactory;
import com.solipsys.system.EnvUtil;
import com.solipsys.util.AutoSave;
import com.solipsys.view.AWTViewCanvas;
import com.solipsys.view.View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.mse.trackhooknotifier.TrackHookNotifier;

/**
 * Layout configuration for the TacSit.
 **/
public class TdfLayout
{
  public static final String DASHBOARD_AUTOSAVE = "DASHBOARD_AUTOSAVE";
  public static final String DASHBOARD_DEFAULT_CLOSED = "DASHBOARD_DEFAULT_CLOSED";
  public static final String FORCE_DASHBOARD = "FORCE_DASHBOARD";

  public static final String DOCKMANAGER_NAME = "DOCKMANAGER_NAME";

  protected static Logger elog = Logger.getLogger( Layout.class );

  public Map layout( Object ref, Map map )
  {
      
    Application app = (Application)ref;
    Logger log = Logger.getLogger( "startup" );

    log.info( "Layout: Laying out the Tacsit..." );

    View v = (View)map.get( ContextConstants.VIEW );
    log.info( "Layout: Initializing the DockManager" );
    System.out.println("Add thn to view container");
    TrackHookNotifier thn = new TrackHookNotifier();
    v.addViewSelectListener(thn);
    System.out.println("After adding listeners");
    DockManager dmgr = initializeDockManager( v, app, map );

    log.info( "Layout: Building view canvas" );
    AWTViewCanvas viewCanvas = new AWTViewCanvas( v );

    Container viewComponent;

    Object obj = map.get( FORCE_DASHBOARD );
    if( EnvUtil.getBoolean( "tacSit.showDashboard", false ) ||
        ( obj instanceof Boolean && ((Boolean)obj).booleanValue() ) )
    {
      viewComponent = createDashboardContainer( v, app, map );
    }
    else
    {
      viewComponent = app.getContentPane();
      viewComponent.setLayout( new BorderLayout() );
    }

    // layout the tacsit in the center w/ bevel
    JPanel p = new JPanel( new BorderLayout() );
    p.setBorder( new BevelBorder( BevelBorder.LOWERED ));
    p.add( viewCanvas, BorderLayout.CENTER );
    viewComponent.add( p, BorderLayout.CENTER );

    log.info( "Layout: Building Docks" );

    createLeftRightDocks( dmgr, viewComponent );

    Component north = createNorthPanel( dmgr );
    if( north != null )
    {
      viewComponent.add( north, BorderLayout.NORTH );
    }

    Component south = createSouthPanel( dmgr );
    if( south != null )
    {
      // add south toolbar directly to app content pane so it will
      // not be affected by the dashboard split-pane
      app.getContentPane().add( south, BorderLayout.SOUTH );
    }

    Map rtn = new HashMap();
    rtn.put( "controlBar", south );
    return rtn;
  }

  protected DockManager initializeDockManager( View v, Application app, Map map )
  {
    String key = (String)map.get( DOCKMANAGER_NAME );
    if( key == null ) key = "TacSit";

    DockManager dmgr = new DockManager( key, app, ToolBarGroup.sharedInstance( key ) );

    HashMap cmap = new HashMap();
    cmap.put( ContextConstants.VIEW, v );
    cmap.put( InstanceContext.INSTANCE_CONTEXT, new InstanceContext.View() );

    dmgr.setContext( cmap );
    DockManagerGroup dgrp = DockManagerGroup.sharedInstance( key );
    DockManager initial = (DockManager)dgrp.get( "Standard" );
    if( initial != null )
    {
      dmgr.set( initial );
    }

    DockManager.addToAutoSave( dmgr, "toolbars/dockLayout/" + key, app );
    return dmgr;
  }

  protected void createLeftRightDocks( DockManager dmgr, Container viewComponent )
  {
    viewComponent.add( createDocks( dmgr, "TopWest", "BottomWest",
                                    SwingConstants.VERTICAL ), BorderLayout.WEST );
    viewComponent.add( createDocks( dmgr, "TopEast", "BottomEast",
                                    SwingConstants.VERTICAL ), BorderLayout.EAST );
  }

  protected Component createNorthPanel( DockManager dmgr )
  {
    JPanel north = new JPanel( new ListLayout() );

    JPanel chatAlert = new JPanel( new GridLayout( 1, 2 ) );
    chatAlert.add( createChatPanel() );
    chatAlert.add( createAlertPanel() );

    north.add( chatAlert );
    north.add( createDocks( dmgr, "LeftNorth", "RightNorth",
                            SwingConstants.HORIZONTAL ) );
    return north;
  }

  protected Component createChatPanel()
  {
    return new RecentChatPanel();
  }

  protected Component createAlertPanel()
  {
    return new RecentAlertPanel();
  }

  protected Component createSouthPanel( DockManager dmgr )
  {
    JPanel south = new JPanel( new ListLayout() );
    south.add( createDocks( dmgr, "TopLeftSouth", "TopRightSouth",
               SwingConstants.HORIZONTAL ) );
    south.add( createDocks( dmgr, "BottomLeftSouth", "BottomRightSouth",
               SwingConstants.HORIZONTAL ) );
    return south;
  }

  protected Container createDashboardContainer( View v, Application app, Map map )
  {
    String dbKey = (String)map.get( DASHBOARD_AUTOSAVE );
    if( dbKey == null ) dbKey = "dashboard/TacSit";

    JPanel viewComponent = new JPanel( new BorderLayout() );
    viewComponent.setMinimumSize( new Dimension( 0, 0 ) );

    final Dashboard dashboard = createDashboard();
    ModelEditor dashboardEditor = createDashboardEditor( dashboard );

    HashMap cxt = new HashMap();
    cxt.put( ContextConstants.VIEW, v );
    cxt.put( ContextConstants.COMPONENT, dashboardEditor );

    dashboardEditor.setModel( dashboard );
    dashboard.setContext( cxt );

    AutoSave.getDefault().add( dashboard, dbKey );
    app.addApplicationListener( new ApplicationListenerAdapter()
    {
      public void applicationDestroyed( ApplicationEvent evt )
      {
        AutoSave.getDefault().remove( dashboard );
      }
    });

    final SSplitPane splitPane = new SSplitPane( 
       "dashboard/" + v.getResourceName(), JSplitPane.HORIZONTAL_SPLIT,
       dashboardEditor.getComponent(), viewComponent );

    // make the split pane object accessible
    v.addViewObject( DashboardUtil.DASHBOARD_SPLIT_PANE_NAME, splitPane );

    splitPane.setOneTouchExpandable( true );

    Object obj = map.get( DASHBOARD_DEFAULT_CLOSED );
    boolean defClosed = obj instanceof Boolean && ((Boolean)obj).booleanValue();

    if ( defClosed )
    {
      splitPane.setDefaultClose( SSplitPane.LEFT_CLOSED );
    }
    
    app.getContentPane().add( splitPane, BorderLayout.CENTER );
    return viewComponent;
  }

  protected Dashboard createDashboard()
  {
    return new DefaultDashboard();
  }

  protected ModelEditor createDashboardEditor( Dashboard dashboard )
  {
    return ModelEditorFactory.getDefault().createModelEditor( dashboard );
  }

  protected Component createDocks( DockManager dmgr, String a, String b, int orientation )
  {
    int a1, a2, x2, y2;
    double wx, wy;
    if ( orientation == SwingConstants.VERTICAL )
    {
      a1 = GridBagConstraints.NORTH;
      a2 = GridBagConstraints.SOUTH;
      x2 = 0;
      y2 = 1;
      wx = 0.0;
      wy = 1.0;
    }
    else
    {
      a1 = GridBagConstraints.WEST;
      a2 = GridBagConstraints.EAST;
      x2 = 1;
      y2 = 0;
      wx = 1.0;
      wy = 0.0;
    }

    JPanel p = new JPanel( new GridBagLayout() );
    p.add( dmgr.getDock( a, orientation ),
     new GridBagConstraints( 0, 0, 1, 1, wx, wy, a1,
       GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 1, 1 ));

    Container bw = dmgr.getDock( b, orientation );
    bw.setLayout( new OrientationLayout( OrientationLayout.RIGHT ));
    p.add( bw, new GridBagConstraints( x2, y2, 1, 1, wx, wy, a2,
        GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 1, 1 ));

    return new ButtonScrollPane( p );
  }
}
