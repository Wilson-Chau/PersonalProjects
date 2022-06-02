FLAGS = -pedantic-errors -std=c++11

check_valid_bet.o: check_valid_bet.cpp check_valid_bet.h
	g++ $(FLAGS) -c $<

gainorlose.o: gainorlose.cpp gainorlose.h
	g++ $(FLAGS) -c $<

Blackjack.o: Blackjack.cpp Blackjack.h check_valid_bet.h gainorlose.h
	g++ $(FLAGS) -c $<
      
sicbo.o: sicbo.cpp sicbo.h check_valid_bet.h gainorlose.h
	g++ $(FLAGS) -c $<
      
tiger_machine.o: tiger_machine.cpp tiger_machine.h check_valid_bet.h gainorlose.h
	g++ $(FLAGS) -c $<

casino.o: casino.cpp check_valid_bet.h gainorlose.h Blackjack.h sicbo.h tiger_machine.h
	g++ $(FLAGS) -c $<

casino: casino.o check_valid_bet.o gainorlose.o Blackjack.o sicbo.o tiger_machine.o
	g++ $(FLAGS) $^ -o $@
  
clean:
	rm -f main main.o check_valid_bet.o gainorlose.o Blackjack.o sicbo.o tiger_machine.o

.PHONY: clean
