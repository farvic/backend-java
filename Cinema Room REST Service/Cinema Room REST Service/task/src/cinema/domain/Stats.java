package cinema.domain;

public class Stats {
    private int currentIncome;
    private int numberOfAvailableSeats;
    private int numberOfPurchasedTickets;

    public Stats(int currentIncome, int numberOfAvailableSeats, int numberOfPurchasedTickets) {
        this.currentIncome = currentIncome;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }

    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "current_income:" + currentIncome +
                ", number_of_available_seats:" + numberOfAvailableSeats +
                ", number_of_purchased_tickets:" + numberOfPurchasedTickets +
                '}';
    }

    public String toJsonString() {
        return "{" +
                "\"current_income\":" + currentIncome +
                ",\"number_of_available_seats\":" + numberOfAvailableSeats +
                ",\"number_of_purchased_tickets\":" + numberOfPurchasedTickets +
                "}";
    }
}
