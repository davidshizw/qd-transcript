package jumpstart;

public class Student {
	private static final String CEEB_CODE = "694592";
	private static final String UCAS_CODE = "50032";
	private static final String IB_CODE = "050477";
	
	private String name;
	private String _id;
	private String gender;
	private String doc;
	private String dob;
	private String nationality;
	private String dog;
	private String doi;
	
	private Grade[] g10_grade;
	private Grade[] g11_grade;
	private Grade[] g12_grade;
	
	public Student(String[] args) {
		name = args[0];
		_id = args[1];
		gender = args[2];
		doc = args[3];
		dob = args[4];
		nationality = args[5];
		dog = args[6];
		doi = args[7];
		g10_grade = null;
		g11_grade = null;
		g12_grade = null;
	}
	
	public boolean check() {
		if(g10_grade == null || g11_grade == null || g12_grade == null) {
			return false;
		}
		if(name.equals("") || _id.equals("") || gender.equals("") || doc.equals("") || dob.equals("") || nationality.equals("") || dog.equals("") || doi.equals("")) {
			return false;
		}
		return true;
	}
	
	public String getID() {
		return _id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getdoc() {
		return doc;
	}
	
	public String getCEEB_CODE() {
		return CEEB_CODE;
	}
	
	public String getUCAS_CODE() {
		return UCAS_CODE;
	}
	
	public String getIB_CODE() {
		return IB_CODE;
	}
	
	public String getdob() {
		return dob;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public String getdog() {
		return dog;
	}
	
	public String getdoi() {
		return doi;
	}
	
	public Grade[] getG10Grade() {
		return g10_grade;
	}
	
	public Grade[] getG11Grade() {
		return g11_grade;
	}
	
	public Grade[] getG12Grade() {
		return g12_grade;
	}
	
	public void setG10Grade(Grade[] args) {
		g10_grade = args;
	}
	
	public void setG11Grade(Grade[] args) {
		g11_grade = args;
	}
	
	public void setG12Grade(Grade[] args) {
		g12_grade = args;
	}
}
