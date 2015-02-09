<!-- Dummy JSP to demonstrate Ant build process -->
<html>
  <head>
    <title>MyWebapp: Login page</title>
  </head>
  <body>
    <h1>MyWebapp: Login page</h1>
    <br/><br/>
    <form action="/demo/login" method="POST" >
      <table width="75%">
        <tr> 
          <td width="48%">Username</td>
          <td width="52%">
            <input type="text" name="name" />
          </td>
        </tr>
	<tr>&nbsp;</tr>
        <tr> 
          <td width="48%">Password</td>
          <td width="52%">
            <input type="password" name="pass" />
          </td>
        </tr>
      </table>
      <p> 
        <input type="submit" name="Submit" value="Submit name" />
        <input type="reset" name="Reset" value="Reset form" />
      </p>
    </form>
  </body>
</html>
