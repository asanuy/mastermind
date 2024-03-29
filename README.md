## Mastermind API

Mastermind is a code-breaking game for two players. One player becomes the codemaker, the other the codebreaker. The codemaker chooses a pattern of four pegColor code pegs (duplicates allowed) and the codebreaker tries to guess it, in both order and pegColor. Each guess is made by placing a row of pegColor code pegs on the decoding board. Once placed, the codemaker provides feedback by placing from zero to four key pegs in the small holes of the row with the guess. A black key peg (small red in the image) is placed for each code peg from the guess which is correct in both pegColor and position. A white key peg indicates the existence of a correct pegColor code peg placed in the wrong position.

### Documentation

Mastermind API is a Spring Web application with an embedded Tomcat that provides a collection of RESTful APIs following HAL specification that simulate the role of the codemaker. Find below a sample of the main APIs.

### Create a new game as codekamer

* **URL**

  ```
  POST localhost:8080/games
  ```

* **Headers**

  * **Content-Type:** application/json

* **URL Params**

  None

* **Data Params**

  Pattern of four colors chosen by the codemaker.

  Example of the body in the POST request:

  ```
  [
    {
      "pegColor": "RED"
    }, {
      "pegColor": "RED"
    }, {
      "pegColor": "BLACK"
    }, {
      "pegColor": "BLUE"
    }
  ]
  ```

  Available colors to provide in the pattern are **RED, BLUE, GREEN, YELLOW, WHITE & BLACK**

* **Success Response**

    * **Code:** 201

* **Error Response**

    * **Code:** 500

### Make a guess of the last game created as a codebreaker

* **URL (example)**

  ```
  GET localhost:8080/games/guess
  ```

* **Headers**

  * **Content-Type:** application/json

* **URL Params**

    **Required:**

   ```pegColors=[COLORS]```

   Example:

   ```
   GET localhost:8080/games/guess?pegColors=BLACK,RED,BLUE,RED
   ```


* **Data Params**

  None

* **Success Response**

  A black key peg is placed for each code peg from the guess which is correct in both color and position. A white key peg indicates the existence of a correct color code peg placed in the wrong position.

    * **Code:** 200

* **Error Response**

    * **Code:** 400
    * **Code:** 404
    * **Code:** 500