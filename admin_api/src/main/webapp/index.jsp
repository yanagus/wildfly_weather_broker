<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Enter city</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
    <h2>Enter city</h2>

    <c:if test="${not empty infoMessage}">
      <p style="color: red; font-weight: bold;">
          <c:out value="${infoMessage}"/>
      </p>
    </c:if>

    <form id="city" action="update" method="POST">
        <table>
            <tr>
                <td style="text-align: right;"><label for="name">City:</label>
                </td>
                <td><input type="text" id="name" name="name"
                    value="${city.name}" placeholder = "Moscow or New York" /></td>
                <td><label style="color: red; width: 100%;text-align: left;">${errorMessageName}</label></td>
            </tr>
            <tr>
                <td style="text-align: right;"><label for="region">Region:</label>
                </td>

                <td><input type="text" id="region" name="region"
                    value="${city.region}" placeholder="Moscow Oblast or NY"/>
                </td>
                <td><label style="color: red; width: 100%;text-align: left;">${errorMessageRegion}</label></td>
            </tr>
        </table>
        <p>
            <input id="submit" type="submit" value="Submit" />
        </p>

        <p>
            <label style="color: green;width: 100%;text-align: left;">${infoMessage}</label>
        </p>

        <p>
            <label style="color: red; width: 100%;text-align: left;">${errorMessage}</label>
        </p>
    </form>

</body>
</html>