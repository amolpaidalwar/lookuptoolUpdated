
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<title>Shopper's Stop</title>
<head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous" />
</head>
<style>
  .navbar {
      border-radius: 0px;
      height: 60px;
  }
  @import url(http://fonts.googleapis.com/css?family=Open+Sans:400,700);
body {
  font-family: 'Open Sans', 'sans-serif';
}
.footer {
   position: fixed;
   left: 0;
   bottom: 0;
   width: 100%;
   background-color: black;
   color: white;
   text-align: center;
   padding: 10px;
   height: 60px;

}
.side-menu {
  position: fixed;
  width: 300px;
  height: 100%;
  background-color: #f8f8f8;
  border-right: 1px solid #e7e7e7;
}
.side-menu .navbar {
  border: none;
}
.side-menu .navbar-nav .active a {
  background-color: transparent;
  margin-right: -1px;
  border-right: 5px solid #e7e7e7;
}
.side-menu .navbar-nav li {
  display: block;
  width: 100%;
  border-bottom: 1px solid #e7e7e7;
}
.side-menu .navbar-nav li a {
  padding: 15px;
}
.side-menu .navbar-nav li a .glyphicon {
  padding-right: 10px;
}

/* small screen */
@media (max-width: 768px) {
  .side-menu {
    position: relative;
    width: 100%;
    height: 0;
    border-right: 0;
    border-bottom: 1px solid #e7e7e7;
  }
  .side-menu-container > .navbar-nav {
    /* Add position:absolute for scrollable menu -> see top comment */
    position: fixed;
    left: -300px;
    width: 300px;
    top: 43px;
    height: 100%;
    border-right: 1px solid #e7e7e7;
    background-color: #f8f8f8;
   
    -webkit-transform-style: preserve-3d;
    transform-style: preserve-3d;
  }

  
  /* Hamburger */
  .navbar-toggle {
    border: 0;
    float: left;
    padding: 18px;
    margin: 0;
    border-radius: 0;
    background-color: #f3f3f3;
  }

  .navbar-header {
    /* this is probably redundant */
    position: fixed;
    z-index: 3;
    background-color: #f8f8f8;
  }
  
}
.row{
	margin-right: 0px;
}
</style>
<body>

	<div class="header"  >
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</div>

	<div class="row" >

           <div class="col-md-3">
		        <tiles:insertAttribute name="menu"></tiles:insertAttribute>
           </div>
			<div class="col-md-9">
				<tiles:insertAttribute name="body"></tiles:insertAttribute>
			</div>
	</div>


	<footer>
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	</footer>
</body>


</html>