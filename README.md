# Rock Paper Scissors Game

This project is a simple REST API for playing the game "Rock paper Scissors" between two players. 
It is implemented using Java and built with Maven using the Spring Boot framework.

**Endpoints**

* POST /api/games
* POST /api/games/{id}/join
* POST /api/games/{id}/move
* GET /api/games/{id}


**How To Run**


From the root folder run:
    
1. $ mvn package
2. $ java -jar target/rockpaperscissors-0.0.1-SNAPSHOT.jar

**How To Use**

Below is an example of one game session using curl from a local terminal on MacOS

1. $ curl --header "Content-Type: application/json" --request POST --data 
'{"name":"Nils"}' http://localhost:8080/api/games 
   * A game will be created and player one joins as Nils.
    * The request returns the UUID of the game which can then be sent to the other player.
    

2. $ curl --header "Content-Type: application/json" --request POST --data '{"name":"Pia"}' 
   http://localhost:8080/api/games/INSERT-UUID-HERE/join
   * If someone else hasn't already connected to the game with the UUID Player Two is set to Pia.
    

3. $ curl --header "Content-Type: application/json" --request POST --data 
   '{"name":"Nils", "move": "Rock"}' http://localhost:8080/api/games/INSERT-UUID-HERE/move
   * Nils choses Rock as their move.
    

4. $ curl --header "Content-Type: application/json" --request POST --data 
   '{"name":"Pia", "move": "Paper"}' http://localhost:8080/api/games/INSERT-UUID-HERE/move
   * Pia choses Paper as their move.
   

5. $ curl --header "Content-Type: application/json" --request GET 
   http://localhost:8080/api/games/INSERT-UUID-HERE
    * This will in this particular case return a response looking like this:
      

      Game ID: (Some UUID)

      Game state: Ended

      Player 1: Nils
      Move: Rock

      Player 2: Pia
      Move: Paper

      RESULT: 
      Player 1 lost. 
      Player 2 won.