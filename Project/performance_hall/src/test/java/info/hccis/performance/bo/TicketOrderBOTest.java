package info.hccis.performance.bo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketOrderBOTest {

    @Test
    void validateHollPassNumber() {

        //Add unit test details to test the new method...

    }

    @Test
    public void testInvalidDiscountCombination() {
        TicketOrderBO bo = new TicketOrderBO();
        int result = bo.calculateDiscountRateFromCode("Richard Smith", "30RR");
        assertEquals(0, result);
    }


    @Test
    public void testValidDiscountCombination() {
        TicketOrderBO bo = new TicketOrderBO();
        int result = bo.calculateDiscountRateFromCode("Richard Smith", "30Ri");
        assertEquals(30, result);
    }

}