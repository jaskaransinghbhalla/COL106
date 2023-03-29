import heap_package.Heap;
import java.util.ArrayList;

public class Poker {

  private int city_size; // City Population
  public int[] money; // Denotes the money of each citizen. Citizen ids are 0,1,...,city_size-1.

  /* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Allowed to use only PriorityQueue data structure globally but can use ArrayList inside methods. 
	   3. Can create at max 4 priority queues.
	*/
  private Heap cityProfitHeap;
  private Heap cityLossHeap;
  private Heap arenaProfitHeap;
  private Heap arenaLossHeap;

  public void initMoney() {
    // Do not change this function.
    for (int i = 0; i < city_size; i++) {
      money[i] = 100000; // Initially all citizens have $100000.
    }
  }

  public Poker(int city_size, int[] players, int[] max_loss, int[] max_profit) {
    // System.out.println("Poker Called");

    /* 
		   1. city_size is population of the city.
		   1. players denotes id of the citizens who have come to the Poker arena to play Poker.
		   2. max_loss[i] denotes the maximum loss player "players[i]"" can bear.
		   3. max_profit[i] denotes the maximum profit player "players[i]"" will want to get.
		   4. Initialize the heap data structure(if required). 
		   n = players.length 
		   Expected Time Complexity : O(n).
		*/

    this.city_size = city_size;
    this.money = new int[this.city_size];
    this.initMoney();
    // To be filled in by the student

    try {
      // Zero Array for Initial Profit and Loss
      int zeroInitializer[] = new int[city_size];
      for (int i = 0; i < city_size; i++) {
        zeroInitializer[i] = 0;
      }
      int profileInitializer[] = new int[city_size];
      for (int i = 0; i < city_size; i++) {
        profileInitializer[i] = i;
      }

      // Negative Max Profits
      for (int i = 0; i < max_profit.length; i++) {
        max_profit[i] = -max_profit[i];
      }
      // Negative Max Losses
      for (int i = 0; i < max_loss.length; i++) {
        max_loss[i] = -max_loss[i];
      }

      // Initializing Heaps for storing Maxiumum Profits and Losses in the City
      this.cityProfitHeap =
        new Heap(city_size, profileInitializer, zeroInitializer);
      this.cityLossHeap =
        new Heap(city_size, profileInitializer, zeroInitializer);
      this.arenaProfitHeap = new Heap(city_size, players, max_profit);
      this.arenaLossHeap = new Heap(city_size, players, max_loss);
      // System.out.println("Poker Exited");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public ArrayList<Integer> Play(int[] players, int[] bids, int winnerIdx) {
    System.out.print("PC_");

    /* 
		   1. players.length == bids.length
		   2. bids[i] denotes the bid made by player "players[i]" in this game.
		   3. Update the money of the players who has played in this game in array "money".
		   4. Returns players who will leave the poker arena after this game. (In case no
		      player leaves, return an empty ArrayList).
                   5. winnerIdx is index of player who has won the game. So, player "players[winnnerIdx]" has won the game.
		   m = players.length
		   Expected Time Complexity : O(mlog(n))
		*/

    // int winner = players[winnerIdx];					// Winner of the game.

    ArrayList<Integer> playersToBeRemoved = new ArrayList<Integer>(); // Players who will be removed after this game.

    // To be filled in by the student

    try {
      // Total Bidden Sum
      int bidSum = 0;
      for (int i = 0; i < bids.length; i++) {
        bidSum = bids[i] + bidSum;
      }

      // Winner's Upgradation
      int winnerId = players[winnerIdx];
      int winnerProfitBid = bidSum - bids[winnerIdx];
      money[winnerId] = money[winnerId] + winnerProfitBid;

      arenaProfitHeap.update(winnerId, winnerProfitBid);
      arenaLossHeap.update(winnerId, -winnerProfitBid);

      cityProfitHeap.update(winnerId, winnerProfitBid);
      // cityLossHeap.update(winnerId, -winnerProfitBid);

      // Winner Out
      if (arenaProfitHeap.getMaxValue() > 0) {
        ArrayList<Integer> maxKeys = arenaProfitHeap.getMax();
        for (int i = 0; i < maxKeys.size(); i++) {
          arenaLossHeap.update(maxKeys.get(i), Integer.MAX_VALUE);
        }

        ArrayList<Integer> removed = arenaProfitHeap.deleteMax();
        arenaLossHeap.deleteMax();

        for (int j = 0; j < removed.size(); j++) {
          playersToBeRemoved.add(removed.get(j));
        }
      }
      // Players Upgradation
      for (int i = 0; i < players.length; i++) {
        if (i != winnerIdx) {
          int losserId = players[i];
          int losserLoss = bids[i];
          money[losserId] = money[losserId] - losserLoss;

          arenaLossHeap.update(losserId, losserLoss);
          arenaProfitHeap.update(losserId, -losserLoss);

          // cityProfitHeap.update(winnerId, -losserLoss);
          cityLossHeap.update(losserId, losserLoss);
        }
        // Losser Out
        if (arenaLossHeap.getMaxValue() > 0) {
          ArrayList<Integer> maxKeys = arenaLossHeap.getMax();
          for (int k = 0; k < maxKeys.size(); k++) {
            arenaProfitHeap.update(maxKeys.get(k), Integer.MAX_VALUE);
          }
          ArrayList<Integer> removed = arenaLossHeap.deleteMax();
          arenaProfitHeap.deleteMax();
          for (int j = 0; j < removed.size(); j++) {
            playersToBeRemoved.add(removed.get(j));
          }
        }
      }

      System.out.print("PE_");
    } catch (Exception e) {
      System.out.println(e);
    }

    // Removal from City

    return playersToBeRemoved;
  }

  public void Enter(int player, int max_loss, int max_profit) {
    System.out.print("EC_");
    /*
			1. Player with id "player" enter the poker arena.
			2. max_loss is maximum loss the player can bear.
			3. max_profit is maximum profit player want to get. 
			Expected Time Complexity : O(logn)
		*/

    // To be filled in by the student
    try {
      // First Time Entry
      // cityProfitHeap.insert(player, 0);
      // cityLossHeap.insert(player, 0);
      arenaProfitHeap.insert(player, -max_profit);
      arenaLossHeap.insert(player, -max_loss);
      System.out.print("EE_");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public ArrayList<Integer> nextPlayersToGetOut() {
    System.out.print("GC_");
    /* 
		   Returns the id of citizens who are likely to get out of poker arena in the next game. 
		   Expected Time Complexity : O(1). 
		*/

    ArrayList<Integer> citizens = new ArrayList<Integer>(); // Players who are likely to get out in next game.

    // To be filled in by the student

    try {
      int profitMaxValue = arenaProfitHeap.getMaxValue();
      int lossMaxValue = arenaLossHeap.getMaxValue();

      if (profitMaxValue > lossMaxValue) {
        ArrayList<Integer> profitRemoval = arenaProfitHeap.getMax();
        for (int j = 0; j < profitRemoval.size(); j++) {
          citizens.add(profitRemoval.get(j));
        }
      } else if (lossMaxValue > profitMaxValue) {
        ArrayList<Integer> lossRemoval = arenaLossHeap.getMax();
        for (int j = 0; j < lossRemoval.size(); j++) {
          citizens.add(lossRemoval.get(j));
        }
      } else {
        ArrayList<Integer> profitRemoval = arenaProfitHeap.getMax();
        ArrayList<Integer> lossRemoval = arenaLossHeap.getMax();
        for (int j = 0; j < profitRemoval.size(); j++) {
          citizens.add(profitRemoval.get(j));
        }
        for (int j = 0; j < lossRemoval.size(); j++) {
          for (int k = 0; k < citizens.size(); k++) {
            if (citizens.get(k) != lossRemoval.get(j)) {
              citizens.add(lossRemoval.get(j));
            }
          }
        }
      }
      System.out.print("GE_");
    } catch (Exception e) {
      System.out.println(e);
    }
    return citizens;
  }

  public ArrayList<Integer> playersInArena() {
    /* 
		   Returns id of citizens who are currently in the poker arena. 
		   Expected Time Complexity : O(n).
		*/

    ArrayList<Integer> citizens = new ArrayList<Integer>(); // citizens in the arena.
    System.out.print("PAC_");
    // To be filled in by the student
    try {
      citizens = arenaProfitHeap.getKeys();
      System.out.print("PAE_");
    } catch (Exception e) {
      System.out.println(e);
    }
    return citizens;
  }

  public ArrayList<Integer> maximumProfitablePlayers() {
    // Full City in Loss case
    System.out.print("MPC_");
    /* 
		   Returns id of citizens who has got most profit. 
			
		   Expected Time Complexity : O(1).
		*/

    ArrayList<Integer> citizens = new ArrayList<Integer>(); // citizens with maximum profit.

    // To be filled in by the student
    try {
      // if (cityProfitHeap.getMaxValue() != 0) {
      if (cityProfitHeap.getMaxValue() >= 0) {
        citizens = cityProfitHeap.getMax();
      }
      // }
    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.print("MPE_");
    return citizens;
  }

  public ArrayList<Integer> maximumLossPlayers() {
    System.out.print("LC_");
    /* 
		   Returns id of citizens who has suffered maximum loss. 
			
		   Expected Time Complexity : O(1).
		*/
    ArrayList<Integer> citizens = new ArrayList<Integer>(); // citizens with maximum loss.

    // To be filled in by the student

    try {
      if (cityLossHeap.getMaxValue() >= 0) {
        citizens = cityLossHeap.getMax();
      }
      System.out.print("LE_");
    } catch (Exception e) {
      System.out.println(e);
    }
    return citizens;
  }
}
