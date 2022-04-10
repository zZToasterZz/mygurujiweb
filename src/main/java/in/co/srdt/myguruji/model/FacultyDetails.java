package in.co.srdt.myguruji.model;

public class FacultyDetails {

		private long facultyid;
		private String facultycode;
		private String emplid; 
		private String designation;
		private String firstname;
		private String emailaddr;
		private String primarycontact;
		private String Pref;
		private String middlename;
		private String lastname;
		private String fullname;
		private String secondarycontact;
		private String createdby;

		public FacultyDetails() {
			
		}

		

		public String getPref() {
			return Pref;
		}



		public void setPref(String pref) {
			Pref = pref;
		}



		public String getMiddlename() {
			return middlename;
		}



		public void setMiddlename(String middlename) {
			this.middlename = middlename;
		}



		public String getLastname() {
			return lastname;
		}



		public void setLastname(String lastname) {
			this.lastname = lastname;
		}



		public String getFullname() {
			return fullname;
		}



		public void setFullname(String fullname) {
			this.fullname = fullname;
		}



		public String getSecondarycontact() {
			return secondarycontact;
		}



		public void setSecondarycontact(String secondarycontact) {
			this.secondarycontact = secondarycontact;
		}



		public String getCreatedby() {
			return createdby;
		}



		public void setCreatedby(String createdby) {
			this.createdby = createdby;
		}



		@Override
		public String toString() {
			return "FacultyDetails [facultyid=" + facultyid + ", facultycode=" + facultycode + ", emplid=" + emplid
					+ ", designation=" + designation + ", firstname=" + firstname + ", emailaddr=" + emailaddr
					+ ", primarycontact=" + primarycontact + ", Pref=" + Pref + ", middlename=" + middlename
					+ ", lastname=" + lastname + ", fullname=" + fullname + ", secondarycontact=" + secondarycontact
					+ ", createdby=" + createdby + "]";
		}



		public long getFacultyid() {
			return facultyid;
		}

		public void setFacultyid(long facultyid) {
			this.facultyid = facultyid;
		}

		public String getFacultycode() {
			return facultycode;
		}

		public void setFacultycode(String facultycode) {
			this.facultycode = facultycode;
		}

		public String getEmplid() {
			return emplid;
		}

		public void setEmplid(String emplid) {
			this.emplid = emplid;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getEmailaddr() {
			return emailaddr;
		}

		public void setEmailaddr(String emailaddr) {
			this.emailaddr = emailaddr;
		}

		public String getPrimarycontact() {
			return primarycontact;
		}

		public void setPrimarycontact(String primarycontact) {
			this.primarycontact = primarycontact;
		}
}
