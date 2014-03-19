using GLib;

public interface CloseControlObserver : GLib.Object {
	public abstract void update(CloseControlObservable o);
}

public interface CloseControlObservable :GLib.Object {
	public abstract void addObserver(CloseControlObserver o);
	public abstract void removeObserver(CloseControlObserver o);
	public abstract void notifyObserver();
}
