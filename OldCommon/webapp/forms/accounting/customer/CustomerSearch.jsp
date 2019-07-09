<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.XactQuery" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

<html>
  <head>
    <title>Customer Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Contact.js"></script>
    <script Language="JavaScript">
      function initForm() {
	     	enableCustomerCriteriaControls(SearchForm, SearchForm.qry_CustomerType.value);
	    }
  	</script>
  </head>

  <%
	 String pageTitle = "Customer Search";
  %>
  
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initForm()">
   <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Accounting.CustomerSearch">
	     <h3><%=pageTitle%></h3>
  		 <%@include file="CustomerSearchCriteriaAndList.jsp"%>

    <!-- Display command buttons -->         
	  <table width="100%" cellpadding="0" cellspacing="0">
		  <tr>
				<td colspan="2">
				  <input name="search" type="button" value="Search" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
				  <input name="reset" type="reset" value="Clear" style="width:90">
				  <input type="button" name="edit" value="Edit" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
                  <input type="button" name="add" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
          <!-- 
            <input type="button" name="delete" value="Delete" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
           -->
				</td>
		  </tr>		
	  </table>
	  <input name="clientAction" type="hidden">
   </form>
 </body>
</html>