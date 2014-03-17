using GLib;


public class CloseControlDataMonitor : GLib.Object {
	
	private string _dbFileName = "/tmp/tdfTracks.db"; 
	private string _dbJournalName = "/tmp/tdfTracks-wal.db";
	private FileMonitor _dbMonitor = null;
	private FileMonitor _dbJournalMonitor = null;

	public string dbFile {
		get { return _dbFileName; }
	}

	public string dbJournal {
		get { return _dbJournalName; }
	}
	public FileMonitor dbMonitor {
		get { return _dbMonitor;}
	}

	public FileMonitor dbJournalMonitor {
		get { return _dbJournalMonitor; }
	}
	
	public CloseControlDataMonitor() {
		try {
			stderr.printf("CloseControlData - constructed\n");
			File db = File.new_for_path(this._dbFileName);
			File dbj = File.new_for_path(this._dbJournalName);
			this._dbMonitor = db.monitor(FileMonitorFlags.NONE, null);
			this._dbJournalMonitor = dbj.monitor(FileMonitorFlags.NONE, null);
			
			addMonitorCallbacks();
		}
		catch(Error err) {
			stderr.printf("Error: %s\n", err.message);
		}
	}

	public void mainLoop() {
		MainLoop loop = new MainLoop();
		loop.run();
	}

	protected void addMonitorCallbacks() {

		this.dbMonitor.changed.connect((src, dest, event) => {

				if (FileMonitorEvent.CHANGES_DONE_HINT != event) {
					return;
				}
				stderr.printf("%s Changed\n", this.dbFile);
			});

		this.dbJournalMonitor.changed.connect((src, dest, event) => {

				if (FileMonitorEvent.CHANGES_DONE_HINT != event) {
					return;
				}
				stderr.printf("%s Changed\n", this.dbJournal);
			});
	}
}
