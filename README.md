
# Robot
Main task of Robot project is collecting information from different 
bookstore and provide users information about best promotions 
and free books.

## Authors

 - Artur Rosa
 - Mateusz Bratek
 - Paweł Stabryła 
  
## Run instruction 
 
### TEMPORARY 
    
   Deploy by IDE on Tomcat Servlet Container. 
   In addition change user and password in hibernate.properties file to local sql value
 
## Kanban board - managing tasks

https://waffle.io/Ice94/robot

## Code quality

 - Run SonarQube server on default port (9000).
      
 - Move to project directory and run:

        cd scripts/
        ./raportGenerator.sh

#### Reports
 - Jacoco report
 - Checkstyle
 - FindBugs
 - Sonar
  

## Requirements 
 
 ### Minimum requirements
1. Robot collects book information for gratis and promo books
2. Book information collected at minimum is: title, genre, author(s), promo details, price
3. If book has subtitle, it's collected as well
4. Web-UI
5. Book information is collected from 3 DIFFERENT libraries (screen scraping or REST API)
6. Robot runs once per day, results are appended
7. Run results are persisted in DB
8. Runs are logged along with their statistics 
9. Project mantra

 ### Recommended requirements (badge award)
1. Minimum requirements
2. Results can be sorted per all fields
3. Results can be filtered per bookstore
4. Results can be filtered per category
5. Results can be filtered per author
6. Filters can stack
7. Promotions and free books are marked differently
8. I can search for books (author, title, title fragment, price greater or lower than, price range)
9. Code coverage 75% and higher (branch, and line, lowest tool result counts)
10. User documentation (how to run, install, etc. preferably with scripts)
11. At least one library needs to be scraped for results
12. At least one library results are to be from it's REST API
13. Book information is collected from 5 DIFFERENT libraries
14. Holy master, Jenkins, code reviews, Sonar, Maven quality plugins
15. Development documentation required, showing project architecture
16. Multi-module Maven project
17. Front-end tech stack is your pick
18. Spring Data with JPA repository and Hibernate for persistance. 

 ### Extra requirements (1 - 2 badges)

1. Recommended requirements (not having this means no Academy badges)
2. Code coverage higher than 80%
3. Book information is collected from 7 or more DIFFERENT libraries
4. I can filter results to show only new additions or only unchanged items
5. JS front-end

## Estimations

https://github.com/Ice94/robot/wiki

 

