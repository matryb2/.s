import java.util.Scanner;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    private double saldo;

    public void wplata(double kwota) throws IllegalArgumentException {
        if (kwota <= 0) {
            throw new IllegalArgumentException("Kwota wpłaty musi być większa od zera.");
        }
        saldo += kwota;
        System.out.println("Wpłacono: " + kwota + ". Nowe saldo: " + saldo);
    }

    public void wyplata(double kwota) throws InsufficientFundsException, IllegalArgumentException {
        if (kwota <= 0) {
            throw new IllegalArgumentException("Kwota wypłaty musi być większa od zera.");
        }
        if (kwota > saldo) {
            throw new InsufficientFundsException("Niewystarczające środki na koncie. Saldo: " + saldo);
        }
        saldo -= kwota;
        System.out.println("Wypłacono: " + kwota + ". Nowe saldo: " + saldo);
    }

    public double getSaldo() {
        return saldo;
    }

    public static void main(String[] args) {
        BankAccount konto = new BankAccount();
        Scanner scanner = new Scanner(System.in);
        BankAccountUI ui = new BankAccountUI(scanner, konto);
        ui.start();
        scanner.close();
    }
}


class BankAccountUI {
    private Scanner scanner;
    private BankAccount konto;

    public BankAccountUI(Scanner scanner, BankAccount konto) {
        this.scanner = scanner;
        this.konto = konto;
    }

    public void start() {
        boolean kontynuuj = true;

        while (kontynuuj) {
            pokazMenu();
            int wybor = scanner.nextInt();

            switch (wybor) {
                case 1:
                    wykonajWplate();
                    break;

                case 2:
                    wykonajWyplate();
                    break;

                case 3:
                    System.out.println("Aktualne saldo: " + konto.getSaldo());
                    break;

                case 4:
                    kontynuuj = false;
                    System.out.println("Dziękujemy za skorzystanie z systemu bankowego.");
                    break;

                default:
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private void pokazMenu() {
        System.out.println("Wybierz opcję:");
        System.out.println("1. Wpłata");
        System.out.println("2. Wypłata");
        System.out.println("3. Sprawdź saldo");
        System.out.println("4. Wyjście");
    }

    private void wykonajWplate() {
        System.out.print("Podaj kwotę do wpłaty: ");
        double kwotaWplaty = scanner.nextDouble();
        try {
            konto.wplata(kwotaWplaty);
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void wykonajWyplate() {
        System.out.print("Podaj kwotę do wypłaty: ");
        double kwotaWyplaty = scanner.nextDouble();
        try {
            konto.wyplata(kwotaWyplaty);
        } catch (InsufficientFundsException e) {
            System.out.println("Błąd: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }
}