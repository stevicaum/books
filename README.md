# HOW TO RUN
1. Run Postgres: from directory /docker/dev run command ``docker-compose up``
2. Use java corretto 21 and run maven3 command ``mvn clean install``
3. run main class ``org.books.BooksApp``
4. Security is enabled by default
5. Run JwtAdminGenerator to generate Admin token or JwtUserGenerator to generate User token to form request:
   ``curl --location 'http://localhost:8080/authors' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlzcyI6Imh0dHA6Ly9ib29rcy5jb20iLCJpYXQiOjE3MTUxNjEwNTAsImV4cCI6MTcxNTUyMTA1MH0._G-R9Yi3t1JHEadf1HR7UfY8yFB7IQ1uVZTU2yEqzmk'``
6. http://localhost:8080/swagger-ui/index.html
7. To run without security, run main class ``org.books.BooksApp`` with VM options ``-Dspring.profiles.active=no-security``




