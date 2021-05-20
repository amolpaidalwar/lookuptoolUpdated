package com.ssl.json;

import java.util.List;

import com.ssl.data.SchemePriceData;

public class SchemePriceJsonObject {


	  int iTotalRecords;

	    int iTotalDisplayRecords;

	    String sEcho;

	    String sColumns;
	    
	    List<SchemePriceData> aaData;

		public int getiTotalRecords() {
			return iTotalRecords;
		}

		public void setiTotalRecords(int iTotalRecords) {
			this.iTotalRecords = iTotalRecords;
		}

		public int getiTotalDisplayRecords() {
			return iTotalDisplayRecords;
		}

		public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
			this.iTotalDisplayRecords = iTotalDisplayRecords;
		}

		public String getsEcho() {
			return sEcho;
		}

		public void setsEcho(String sEcho) {
			this.sEcho = sEcho;
		}

		public String getsColumns() {
			return sColumns;
		}

		public void setsColumns(String sColumns) {
			this.sColumns = sColumns;
		}

		public List<SchemePriceData> getAaData() {
			return aaData;
		}

		public void setAaData(List<SchemePriceData> aaData) {
			this.aaData = aaData;
		}

}
