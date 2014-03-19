using SQLHeavy;

public class CloseControlDataUpdate : GLib.Object, CloseControlObserver {

	private Database db = null;
	
	private string _dbName = "/tmp/tdfTracks.db";

	public string dbName {
		get { return _dbName; }
	}

	public CloseControlDataUpdate() {
		try {
			db = new SQLHeavy.Database(this.dbName, FileMode.READ);
		}
	
		catch (SQLHeavy.Error e) {
			stderr.printf("Error: %s\n ", e.message);
		}
	}
	
	~CloseControlDataUpdate() {
		stderr.printf("~CloseControlDataUpdate()\n");
	}

	public SQLHeavy.QueryResult getPriHook() {
		SQLHeavy.QueryResult rslt = null;
		try {
			string query = "SELECT tn, "
			+"category, "
			+"ident, "
			+"platform, "
			+"latitude, "
			+"longitude, " 
			+"speed, "
			+"altitude, "
			+"course "
			+" FROM track_hooks WHERE id=1;";
			
			stderr.printf("%s\n", query);
			rslt = db.execute(query);
			return rslt;

		}
		catch(SQLHeavy.Error e) {
			stderr.printf("Error: %s\n ", e.message);
			return rslt;
		}
	}

	public void update(CloseControlObservable o) {
		stderr.printf("Hook Database changed\n");
		SQLHeavy.QueryResult r = getHooks();
		stderr.printf("field_count: %d\n", r.field_count);
	}
	
}