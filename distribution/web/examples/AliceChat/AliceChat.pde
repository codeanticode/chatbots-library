/*
A little example using the classic "Eliza" program.

Eliza was compiled as a Processing library, based on the
java source code by Charles Hayden:
htp://www.chayden.net/eliza/Eliza.html

The default script that determines Eliza's behaviour can be 
changed with the readScript() function.
Intructions to modify the script file are available here:
http://www.chayden.net/eliza/instructions.txt
*/

import codeanticode.chatbots.alice.*;

Alice alice;
PFont font;
String aliceResponse, humanResponse;
boolean showCursor;
int lastTime;

void setup()
{
    size(400, 400);

    // When Alice is initialized, a default script built into the
    // library is loaded.
    alice = new Alice(this);
  
  
    font = loadFont("Rockwell-24.vlw");
    textFont(font);

    printAliceIntro();
    humanResponse = "";
    showCursor = true;
    lastTime = 0;
}

void draw()
{
    background(102);

    fill(255);
    text(aliceResponse, 10, 50, width - 40, height);

    fill(0);

    int t = millis();
    if (t - lastTime > 500)
    {
        showCursor = !showCursor;
        lastTime = t;
    }
    
    if (showCursor) text(humanResponse + "_", 10, 150, width - 40, height);
    else text(humanResponse, 10, 150, width - 40, height);
}

void keyPressed() 
{
    if ((key == ENTER) || (key == RETURN)) 
    {
        println(humanResponse);
        aliceResponse = alice.processInput(humanResponse);
        println(">> " + aliceResponse);
        humanResponse = "";
    } 
    else if ((key > 31) && (key != CODED)) 
    {
        // If the key is alphanumeric, add it to the String
        humanResponse = humanResponse + key;
    }
    else if ((key == BACKSPACE) && (0 < humanResponse.length()))
    {
        char c = humanResponse.charAt(humanResponse.length() - 1);
        humanResponse = humanResponse.substring(0, humanResponse.length() - 1);
    }
}

void printAliceIntro()
{
    String hello = "Hello.";
    aliceResponse = hello + " " + alice.processInput(hello);
    println(">> " + aliceResponse);
}