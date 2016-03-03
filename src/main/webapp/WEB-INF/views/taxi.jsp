<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Taxi Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<h1>
	Add a Taxi
</h1>

<c:url var="addAction" value="/taxi/add" ></c:url>

<form:form action="${addAction}" commandName="taxi">
<table>
	<%-- <c:if test="${!empty taxi.password}"> --%>
	<tr>
		<td>
			<form:label path="taxiId">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="taxiId" readonly="true" size="8"  disabled="true" />
			<form:hidden path="taxiId" />
		</td> 
	</tr>
	<%-- </c:if> --%>
	<tr>
		<td>
			<form:label path="password">
				<spring:message text="Password"/>
			</form:label>
		</td>
		<td>
			<form:input path="password" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="actualState">
				<spring:message text="ActualState"/>
			</form:label>
		</td>
		<td>
			<form:input path="actualState" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="futureState">
				<spring:message text="FutureState"/>
			</form:label>
		</td>
		<td>
			<form:input path="futureState" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<c:if test="${taxi.taxiId!=0}">
				<input type="submit"
					value="<spring:message text="Edit Taxi"/>" />
			</c:if>
			<c:if test="${taxi.taxiId==0}">
				<input type="submit"
					value="<spring:message text="Add Taxi"/>" />
			</c:if>
		</td>
	</tr>
</table>	
</form:form>
<br>
<h3>Taxis List</h3>
<c:if test="${!empty getTaxis}">
	<table class="tg">
	<tr>
		<th width="80">Taxi ID</th>
		<th width="120">Taxi ActualState</th>
		<th width="120">Taxi FutureState</th>
		<th width="120">Taxi Password</th>
		<th width="120">Taxi Position</th>
		<th width="60">Edit</th>
		<th width="60">Delete</th>
	</tr>
	<c:forEach items="${getTaxis}" var="taxi">
		<tr>
			<td>${taxi.taxiId}</td>
			<td>${taxi.actualState}</td>
			<td>${taxi.futureState}</td>
			<td>${taxi.password}</td>
			<td>${taxi.position}</td>
			<td><a href="<c:url value='/edit/${taxi.taxiId}' />" >Edit</a></td>
			<td><a href="<c:url value='/remove/${taxi.taxiId}' />" >Delete</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
