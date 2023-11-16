package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.UseDeathNote;

class TestDeathNote {
    final int WRONG_NUM_RULE = 0;
    DeathNote DN = new UseDeathNote();


    /**
     * Cheks if wrong rule number is given, we get an IllegalArgumentExeption back.
    */
    @Test
    public void testGetRule(){
        try{
            DN.getRule(WRONG_NUM_RULE);
            Assertions.fail("Is expected that the getRoule() fails, but it does not");
        }catch (IllegalArgumentException e){
            assertEquals("""
                Given rule number is smaller than 1 or larger
                than the number of rules
                """, e);
        }
    }

}