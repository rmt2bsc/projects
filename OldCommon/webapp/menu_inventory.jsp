<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page import="com.bean.RMT2SessionBean" %>

<gen:InitAppRoot id="APP_ROOT"/>    
<gen:InitSessionBean id="SESSION_BEAN"/>

<%
   String login = SESSION_BEAN == null ? "!@#$%^&(*)" : SESSION_BEAN.getLoginId();
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Xact.js"></script>
<script>
    function handleAction(selection) { 
      switch (selection.name) {
          case "m_logoff":
              parent.menuFrame.location.href = MENU_MAIN;
              menuForm.action = "<%=APP_ROOT%>/reqeustProcessor/Security.Authentication?clientAction=logoff";
              menuForm.target = "_top";
              break;		      
          case "m_user":
              parent.menuFrame.location.href = MENU_MAIN;
              menuForm.action = "/authentication/requestProcessor/User.Search?clientAction=newsearch&<%=AuthenticationConst.AUTH_PROP_USERID%>=<%=login%>";
              menuForm.target = "windowFrame";
              break;
          case "m_codes":
              parent.menuFrame.location.href = MENU_MAIN;
              menuForm.action = "<%=APP_ROOT%>/reqeustProcessor/GeneralCodeGroup.GeneralCodeGroupList?clientAction=list";
              menuForm.target = "windowFrame";
              break;
          case "m_contacts":
              parent.menuFrame.location.href = MENU_MAIN;
              menuForm.action = "<%=APP_ROOT%>/contactservlet?clientAction=start";
              menuForm.target = "windowFrame";
              break;
          case "m_gl_maint":
              parent.menuFrame.location.href = MENU_GL_MAINT;
              return;
          case "m_inventory":
              parent.menuFrame.location.href = MENU_INVENTORY;
              return;
          case "m_xact":
              parent.menuFrame.location.href = MENU_XACT;
              return;           
          case "m_projects":
					    parent.menuFrame.location.href = MENU_PROJECTS; 
              return;
       }
       menuForm.submit();
   }
</script>
</head>



 
<body bgcolor="#CCCCCC" text="#000000">
<form name="menuForm" method="post" action="">
  <table width="9%" border="0" bgcolor="#CCCCCC" cellpadding="0" cellspacing="0" height="20%">
    <tr> 
      <td height="1" valign="top" align="center"> 
           <img name="m_logoff" src="<%=APP_ROOT%>/images/logoff.gif" style="border: none" onClick="javascript:handleAction(this)" alt="Logoff User">
      </td>
    </tr>    
    <tr> 
      <td height="1" valign="top"> 
        <input type="button" name="m_contacts" value="Contacts" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr> 
      <td height="1" valign="top"> 
        <input type="button" name="m_gl_maint" value="General Ledger" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr>  
      <td height="1" valign="top"> 
        <input type="button" name="m_inventory" value="Inventory" style=width:120  onClick="handleAction(this)">
      </td>
    </tr>    
    <tr>
      <td height="41" align="center"> <a href="<%=APP_ROOT%>/reqeustProcessor/ItemMaster.Search?clientAction=newsearch" target="windowFrame""> 
          <img src="images/menuitem_item_master.gif" width="55" height="35" style="border:none"></a> 
      </td>
    </tr>    
    <tr>
      <td height="1" valign="top">
        <input type="button" name="m_xact" value="Transactions" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr>
      <td height="1" valign="top"> 
			        <input type="button" name="m_projects" value="Projects" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr>
      <td height="1" valign="top">
        <input type="button" name="m_codes" value="General Codes" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr>
      <td height="1" valign="top">
        <input type="button" name="m_user" value="Security" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
