using GLib;

public class CloseControlApp : GLib.Object {

	public static int main(string[] args) {
		stderr.printf("Starting CloseControl\n");
		CloseControlDataMonitor cd = new CloseControlDataMonitor();
		cd.mainLoop();
		return 0;
	}
}