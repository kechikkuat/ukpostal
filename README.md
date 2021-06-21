Hi All, <br/>
<h1>Introduction</h1>
This main program is to calulcate the distance (in Straight line) between 2 or more coordinate by the UK Postal Code via REST API. <br/>
You can also update the postcode and coordinate via the REST API. </br>
<br/>
<br/>
<h1>Installation</h1>
# clone the repo <br/>
$ git clone https://github.com/kechikkuat/ukpostal.git <br/>
<h4>On Terminal</h4>
Inside the project directory <br/>
`**$ gradlew build** <br/>
**$ java -jar "\absolute\path\to\the\jar" com.geopostal.ukpostal.GeoUkPostalApplication**` <br/>
<h4>On Eclipse/ IntelliJ IDEA Ultimate</h4>
1. Go to the main class <br/>
2. Right click and Run As -> Java Application

<h1>Technology</h1>
1. Java JDK 8<br/>
2. Gradle <br/>
3. Spring Boot REST API<br/>
4. Spring Data JPA<br/>
5. Java Project Lombok<br/>
6. Java Location Tech JTS<br/>
7. H2 Database<br/>

<h3>Note</h3>
To test the generated WKT String you can go here https://arthur-e.github.io/Wicket/sandbox-gmaps3.html and enter input from `generatedWKTString` and you can see the line on Map
<br/>
Work In Progress<br/>
1. Unit Test using JUnit<br/>
2. Add Security when requesting the API
