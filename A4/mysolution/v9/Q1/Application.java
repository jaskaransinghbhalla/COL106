import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application extends Tree {

  public void buildTree(String fileName) {
    // TO be completed by students
    try {
      File file = new File(fileName);
      //Creating a Scanner object
      Scanner sc = new Scanner(file);
      //StringBuffer to store the contents
      StringBuffer sb = new StringBuffer();
      //Appending each line to the buffer
      sb.append(sc.nextLine());
      while (sc.hasNext()) {
        sb.append(" " + sc.nextLine());
      }

      String word = "";

      for (int i = 0; i < sb.length(); i++) {
        if (sb.charAt(i) == 32) {
          if (search(word)) {
            increment(word);
          } else {
            insert(word);
          }
          word = "";
        } else {
          word = word + sb.charAt(i);
        }
      }

      if (search(word)) {
        increment(word);
      } else {
        insert(word);
      }
      sc.close();
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
  }

  public void insert(String s) {
    // TO be completed by students
    if (!search(s)) {
      if (root == null) {
        initalizeRoot(s);
        return;
      } else {
        insertRecursively(root, s);
        return;
      }
    }
  }

  public int increment(String s) {
    // TO be completed by students

    // Getting Node
    TreeNode node = getNodeRecursive(root, s);

    // Helper Variables
    int i = 0;

    // Iterate over Node
    while (i < node.s.size()) {
      // EntryFound
      if (node.s.get(i).compareTo(s) == 0) {
        // Change Value of Node at that place
        node.val.set(i, node.val.get(i) + 1);

        // Values
        int prevMaxValue = node.max_value;
        String prevMaxS = node.max_s;
        int newValue = node.val.get(i);
        String newS = node.s.get(i);

        // Parent Node
        TreeNode parNode = node.parent;

        // Update Count
        node.count = node.count + 1;

        // Update Parent Count
        while (parNode != null) {
          parNode.count = parNode.count + 1;
          parNode = parNode.parent;
        }

        // Update Max_Value
        if (prevMaxValue < newValue) {
          // For Node
          node.max_value = newValue;
          node.max_s = newS;

          // Parent
          while (node.parent != null) {
            if (
              (
                (
                  node.parent.max_value == prevMaxValue &&
                  node.parent.max_s.compareTo(prevMaxS) == 0
                ) ||
                node.parent.max_value < newValue
              )
            ) {
              node.parent.max_value = newValue;
              node.parent.max_s = newS;
              node = node.parent;
            } else {
              break;
            }
          }
        }
        // If Max Value
        else if (prevMaxValue == newValue) {
          if (node.max_s.compareTo(newS) > 0) {
            node.max_s = newS;
            while (node.parent != null) {
              if (
                node.parent.max_value == prevMaxValue &&
                node.parent.max_s.compareTo(prevMaxS) == 0
              ) {
                node.parent.max_s = newS;
              }
              node = node.parent;
            }
          }
        }

        return newValue;
      }
      i++;
    }
    return 0;
  }

  public int decrement(String s) {
    // TO be completed by students
    // Not to be implemented as per Instructor on Piazza
    return 0;
  }

  public int cumulativeFreq(String s1, String s2) {
    // TO be completed by students

    // String Equal
    if (s1.compareTo(s2) == 0) {
      return getVal(s1);
    }
    // String not Equal
    else {
      // Find Common Parent
      TreeNode commonParent = findCommonParent(s1, s2);

      int totalValue = 0;
      // If Both Strings were present in Common Node
      if (searchEntry(commonParent, s1) && searchEntry(commonParent, s2)) {
        // Common Parent 2-Node
        // Case Not Possible

        // Common Parent 3-Node
        if (commonParent.s.size() == 2) {
          totalValue += commonParent.val.get(0);
          if (commonParent.children.size() != 0) {
            totalValue += commonParent.children.get(1).count;
          }
          totalValue += commonParent.val.get(1);
        }
        // Common Parent 4-Node
        else if (commonParent.s.size() == 3) {
          // s1 -> First Position
          if (commonParent.s.get(0).compareTo(s1) == 0) {
            // s2 -> Second Position
            if (commonParent.s.get(1).compareTo(s2) == 0) {
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(1).count;
              }
            }
            // s2 -> Third Position
            else {
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
              totalValue += commonParent.val.get(2);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(1).count;
                totalValue += commonParent.children.get(2).count;
              }
            }
          }

          // s1 at position 1
          if (commonParent.s.get(1).compareTo(s1) == 0) {
            totalValue += commonParent.val.get(1);
            totalValue += commonParent.val.get(2);
            if (commonParent.children.size() != 0) {
              totalValue += commonParent.children.get(2).count;
            }
          }
        }
      }

      // One String in Common Node

      // If S1 is Present
      if (searchEntry(commonParent, s1) && !searchEntry(commonParent, s2)) {
        // Common Parent 2-Node
        // S1 - First Position
        if (commonParent.s.size() == 1) {
          if (commonParent.children.size() != 0) {
            totalValue += countS2(s2, commonParent.children.get(1));
            totalValue += commonParent.val.get(0);
          }
        }
        // Common Parent 3-Node
        else if (commonParent.s.size() == 2) {
          // S1 - First Position
          if (commonParent.s.get(0).compareTo(s1) == 0) {
            // S2 - Second Child
            if (commonParent.s.get(1).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS2(s2, commonParent.children.get(1));
                totalValue += commonParent.val.get(0);
              }
            }
            // S2 - Third Child
            else {
              if (commonParent.children.size() != 0) {
                totalValue += countS2(s2, commonParent.children.get(2));
              }
              totalValue += commonParent.val.get(1);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(1).count;
              }
              totalValue += commonParent.val.get(0);
            }
          }
          // S1 - Second Position
          else if (commonParent.s.get(1).compareTo(s1) == 0) {
            totalValue += countS2(s2, commonParent.children.get(2));
            totalValue += commonParent.val.get(1);
          }
        }
        // Common Parent 4-Node
        else if (commonParent.s.size() == 3) {
          // S1 - First Position
          if (commonParent.s.get(0).compareTo(s1) == 0) {
            // S2 - Second Child
            if (commonParent.s.get(1).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS2(s2, commonParent.children.get(1));
              }
              totalValue += commonParent.val.get(0);
            }
            // S2 - Third Child
            else if (commonParent.s.get(2).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS2(s2, commonParent.children.get(2));
              }
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(1).count;
              }
            }
            // S2 - Fourth Child
            else {
              if (commonParent.children.size() != 0) {
                totalValue += countS2(s2, commonParent.children.get(3));
              }
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
              totalValue += commonParent.val.get(2);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(1).count;
                totalValue += commonParent.children.get(2).count;
              }
            }
          }
          // S1 - Second Position
          else if (commonParent.s.get(1).compareTo(s1) == 0) {
            // S2 - Third Child
            if (commonParent.s.get(2).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS2(s2, commonParent.children.get(2));
              }
              totalValue += commonParent.val.get(1);
            }
            // S2 - Fourth Child
            else {
              if (commonParent.children.size() != 0) {
                totalValue += countS2(s2, commonParent.children.get(3));
              }
              totalValue += commonParent.val.get(1);
              totalValue += commonParent.val.get(2);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(2).count;
              }
            }
          }
          // S1 - Third Position
          else if (commonParent.s.get(2).compareTo(s1) == 0) {
            if (commonParent.children.size() != 0) {
              totalValue += countS2(s2, commonParent.children.get(3));
            }
            totalValue += commonParent.val.get(2);
          }
        }
      }

      // If S2 is Present
      if (!searchEntry(commonParent, s1) && searchEntry(commonParent, s2)) {
        // Common Parent 2-Node
        // S2 - First Position
        // S1 - First Child
        if (commonParent.s.size() == 1) {
          if (commonParent.children.size() != 0) {
            totalValue += countS1(s1, commonParent.children.get(0));
          }
          totalValue += commonParent.val.get(0);
        }
        // Common Parent 3-Node
        else if (commonParent.s.size() == 2) {
          // S2 - First Position
          if (commonParent.s.get(0).compareTo(s2) == 0) {
            if (commonParent.children.size() != 0) {
              totalValue += countS1(s1, commonParent.children.get(0));
            }
            totalValue += commonParent.val.get(0);
          }
          // S2 - Second Position
          else if (commonParent.s.get(1).compareTo(s2) == 0) {
            // S1 - First Child
            if (commonParent.s.get(0).compareTo(s1) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(0));
                totalValue += commonParent.children.get(1).count;
              }
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
            }
            // S1 - Second Child
            else {
              totalValue += commonParent.val.get(1);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(1).count;
              }
            }
          }
        }
        // Common Parent 4-Node
        else if (commonParent.s.size() == 3) {
          // S2 - First Position
          if (commonParent.s.get(0).compareTo(s2) == 0) {
            // S1 - First Child
            if (commonParent.children.size() != 0) {
              totalValue += countS1(s1, commonParent.children.get(0));
            }
            totalValue += commonParent.val.get(0);
          }
          // S2 - Second Position
          else if (commonParent.s.get(1).compareTo(s2) == 0) {
            // S1 - First Child
            if (commonParent.s.get(0).compareTo(s1) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(0));
              }
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(1).count;
              }
            }
            // S1 - Second Child
            else {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(1));
              }
              totalValue += commonParent.val.get(1);
            }
          }
          // S2 - Third Position
          else if (commonParent.s.get(2).compareTo(s2) == 0) {
            //  S1 - First Child
            if (commonParent.s.get(0).compareTo(s1) > 0) {
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
              totalValue += commonParent.val.get(2);
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(0));
                totalValue += commonParent.children.get(1).count;
                totalValue += commonParent.children.get(2).count;
              }
            }
            //  S1 - Second Child
            else if (commonParent.s.get(1).compareTo(s1) > 0) {
              totalValue += commonParent.val.get(1);
              totalValue += commonParent.val.get(2);
              if (commonParent.children.size() != 0) {
                totalValue += commonParent.children.get(2).count;
                totalValue += countS1(s1, commonParent.children.get(1));
              }
            }
            //  S1 - Third Child
            else {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(2));
              }
              totalValue += commonParent.val.get(2);
            }
          }
        }
      }

      // S1 and S2 Absent in Common Parent
      if (!searchEntry(commonParent, s1) && !searchEntry(commonParent, s2)) {
        // 2 Node
        if (commonParent.s.size() == 1) {
          if (commonParent.children.size() != 0) {
            totalValue += countS1(s1, commonParent.children.get(0));
            totalValue += countS2(s2, commonParent.children.get(1));
          }
          totalValue += commonParent.val.get(0);
        }
        // 3 Node
        else if (commonParent.s.size() == 2) {
          // S1 - First Child
          if (commonParent.s.get(0).compareTo(s1) > 0) {
            // S2 - Second Child
            if (commonParent.s.get(1).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(0));
                totalValue += countS2(s2, commonParent.children.get(1));
              }
              totalValue += commonParent.val.get(0);
            }
            // S2 - Third Child
            else {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(0));
                totalValue += countS2(s2, commonParent.children.get(2));
                totalValue += commonParent.children.get(1).count;
              }

              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
            }
          }
          // S1 - Second child
          else if (commonParent.s.get(1).compareTo(s1) > 0) {
            // S2 - Third Child
            if (commonParent.children.size() != 0) {
              totalValue += countS1(s1, commonParent.children.get(1));
              totalValue += countS2(s2, commonParent.children.get(2));
            }
            totalValue += commonParent.val.get(1);
          }
        }
        // 4 Node
        else if (commonParent.s.size() == 3) {
          // S1 - First Child
          if (commonParent.s.get(0).compareTo(s1) > 0) {
            // S2 - Second Child
            if (commonParent.s.get(1).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS2(s2, commonParent.children.get(1));
                totalValue += countS1(s1, commonParent.children.get(0));
              }
              totalValue += commonParent.val.get(0);
            }
            // S2 - Third Child
            else if (commonParent.s.get(2).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(0));
                totalValue += countS2(s2, commonParent.children.get(2));
                totalValue += commonParent.children.get(1).count;
              }
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
            }
            // S2 - Fourth Child
            else {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(0));
                totalValue += countS2(s2, commonParent.children.get(3));
                totalValue += commonParent.children.get(1).count;
                totalValue += commonParent.children.get(2).count;
              }
              totalValue += commonParent.val.get(0);
              totalValue += commonParent.val.get(1);
              totalValue += commonParent.val.get(2);
            }
          }
          // S1 - Second Child
          else if (commonParent.s.get(1).compareTo(s1) > 0) {
            // S2 - Third Child
            if (commonParent.s.get(2).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(1));
                totalValue += countS2(s2, commonParent.children.get(2));
              }
              totalValue += commonParent.val.get(1);
            }
            // S2 - Fourth Child
            else {
              if (commonParent.children.size() != 0) {
                totalValue += countS1(s1, commonParent.children.get(1));
                totalValue += countS2(s2, commonParent.children.get(3));
                totalValue += commonParent.children.get(2).count;
              }
              totalValue += commonParent.val.get(1);
              totalValue += commonParent.val.get(2);
            }
          }
          // S1 - Third Child
          else if (commonParent.s.get(2).compareTo(s1) > 0) {
            if (commonParent.children.size() != 0) {
              totalValue += countS1(s1, commonParent.children.get(2));
              totalValue += countS2(s2, commonParent.children.get(3));
            }
            totalValue += commonParent.val.get(2);
          }
        }
      }

      return totalValue;
    }
  }

  public String maxFreq(String s1, String s2) {
    // TO be completed by students

    // If Strings are Equal then we Simple return their value;
    if (s1.compareTo(s2) == 0) {
      return s1;
    }
    // If Strings not Equal
    else {
      // Find Common Parent
      TreeNode commonParent = findCommonParent(s1, s2);
      String maxString = "";
      int maxValue = 0;

      // If Both Strings were present in Common Node
      if (searchEntry(commonParent, s1) && searchEntry(commonParent, s2)) {
        // Common Parent 2-Node
        // Case Not Possible

        // Common Parent 3-Node
        if (commonParent.s.size() == 2) {
          if (commonParent.children.size() != 0) {
            if (
              commonParent.children.get(1).max_value >= commonParent.val.get(1)
            ) {
              maxString = commonParent.children.get(1).max_s;
              maxValue = commonParent.children.get(1).max_value;
            } else {
              maxString = s2;
              maxValue = commonParent.val.get(1);
            }
          } else {
            maxString = s2;
            maxValue = commonParent.val.get(1);
          }
          if (commonParent.val.get(0) >= maxValue) {
            maxString = s1;
            maxValue = commonParent.val.get(0);
          }
        }
        // Common Parent 4-Node

        if (commonParent.s.size() == 3) {
          if (commonParent.s.get(0).compareTo(s1) == 0) {
            if (commonParent.s.get(1).compareTo(s2) == 0) {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(1).max_value >=
                  commonParent.val.get(1)
                ) {
                  maxString = commonParent.children.get(1).max_s;
                  maxValue = commonParent.children.get(1).max_value;
                } else {
                  maxString = s2;
                  maxValue = commonParent.val.get(1);
                }
              } else {
                maxString = s2;
                maxValue = commonParent.val.get(1);
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxString = s1;
                maxValue = commonParent.val.get(0);
              }
            } else {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(2).max_value >=
                  commonParent.val.get(2)
                ) {
                  maxString = commonParent.children.get(2).max_s;
                  maxValue = commonParent.children.get(2).max_value;
                } else {
                  maxString = s2;
                  maxValue = commonParent.val.get(2);
                }
              } else {
                maxString = s2;
                maxValue = commonParent.val.get(2);
              }

              if (commonParent.val.get(1) >= maxValue) {
                maxString = commonParent.s.get(1);
                maxValue = commonParent.val.get(1);
              }

              if (commonParent.children.size() != 0) {
                if (commonParent.children.get(1).max_value >= maxValue) {
                  maxString = commonParent.children.get(1).max_s;
                  maxValue = commonParent.children.get(1).max_value;
                }
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxString = commonParent.s.get(0);
                maxValue = commonParent.val.get(0);
              }
            }
          }

          // s1 at position 1
          if (commonParent.s.get(1).compareTo(s1) == 0) {
            if (commonParent.children.size() != 0) {
              if (
                commonParent.children.get(2).max_value >=
                commonParent.val.get(2)
              ) {
                maxString = commonParent.children.get(2).max_s;
                maxValue = commonParent.children.get(2).max_value;
              } else {
                maxString = commonParent.s.get(2);
                maxValue = commonParent.val.get(2);
              }
            } else {
              maxString = commonParent.s.get(2);
              maxValue = commonParent.val.get(2);
            }

            if (commonParent.val.get(1) >= maxValue) {
              maxString = s1;
              maxValue = commonParent.val.get(1);
            }
          }
        }
      }

      // If One String was present in Common Node
      // If S1 is Present
      if (searchEntry(commonParent, s1) && !searchEntry(commonParent, s2)) {
        // Common Parent 2-Node
        if (commonParent.s.size() == 1) {
          maxString = commonParent.s.get(0);
          maxValue = commonParent.val.get(0);
          if (commonParent.children.size() != 0) {
            maxString =
              findMaxS2(s2, commonParent.children.get(1), maxValue, maxString);
          }
        }
        // Common Parent 3-Node
        else if (commonParent.s.size() == 2) {
          // s1 at first index
          if (commonParent.s.get(0).compareTo(s1) == 0) {
            // s2 is present in second child
            if (commonParent.s.get(1).compareTo(s2) > 0) {
              maxString = commonParent.s.get(0);
              maxValue = commonParent.val.get(0);
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(1),
                    maxValue,
                    maxString
                  );
              }
            }
            // s2 is present in third child
            else {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(1).max_value >=
                  commonParent.val.get(1)
                ) {
                  maxValue = commonParent.children.get(1).max_value;
                  maxString = commonParent.children.get(1).max_s;
                } else {
                  maxValue = commonParent.val.get(1);
                  maxString = commonParent.s.get(1);
                }
              } else {
                maxValue = commonParent.val.get(1);
                maxString = commonParent.s.get(1);
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxValue = commonParent.val.get(0);
                maxString = commonParent.s.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(2),
                    maxValue,
                    maxString
                  );
              }
            }
          }

          // s1 is present at second position
          if (commonParent.s.get(1).compareTo(s1) == 0) {
            maxValue = commonParent.val.get(1);
            maxString = commonParent.s.get(1);

            maxString =
              findMaxS2(s2, commonParent.children.get(2), maxValue, maxString);
          }
        }

        // Common Parent 4-Node
        if (commonParent.s.size() == 3) {
          // S1 is at index 1
          if (commonParent.s.get(0).compareTo(s1) == 0) {
            // s2 is second child
            if (commonParent.s.get(1).compareTo(s2) > 0) {
              maxString = commonParent.s.get(0);
              maxValue = commonParent.val.get(0);
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(2),
                    maxValue,
                    maxString
                  );
              }
            }
            // s2 is third child
            else if (commonParent.s.get(2).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(1).max_value >=
                  commonParent.val.get(1)
                ) {
                  maxValue = commonParent.children.get(1).max_value;
                  maxString = commonParent.children.get(1).max_s;
                } else {
                  maxValue = commonParent.val.get(1);
                  maxString = commonParent.s.get(1);
                }
              } else {
                maxValue = commonParent.val.get(1);
                maxString = commonParent.s.get(1);
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxValue = commonParent.val.get(0);
                maxString = commonParent.s.get(0);
              }
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(2),
                    maxValue,
                    maxString
                  );
              }
            }
            // s2 is fourth child
            else {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(2).max_value >=
                  commonParent.val.get(2)
                ) {
                  maxValue = commonParent.children.get(2).max_value;
                  maxString = commonParent.children.get(2).max_s;
                } else {
                  maxValue = commonParent.val.get(2);
                  maxString = commonParent.s.get(2);
                }
              } else {
                maxValue = commonParent.val.get(2);
                maxString = commonParent.s.get(2);
              }
              if (commonParent.val.get(1) >= maxValue) {
                maxValue = commonParent.val.get(1);
                maxString = commonParent.s.get(1);
              }

              if (commonParent.children.get(1).max_value >= maxValue) {
                maxValue = commonParent.children.get(1).max_value;
                maxString = commonParent.children.get(1).max_s;
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxValue = commonParent.val.get(0);
                maxString = commonParent.s.get(0);
              }
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(3),
                    maxValue,
                    maxString
                  );
              }
            }
          }

          // s1 is at second position
          if (commonParent.s.get(1).compareTo(s1) == 0) {
            // s2 is third child
            if (commonParent.s.get(2).compareTo(s2) > 0) {
              maxString = commonParent.s.get(1);
              maxValue = commonParent.val.get(1);
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(2),
                    maxValue,
                    maxString
                  );
              }
            }
            // s2 if fourth child
            else {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(2).max_value >=
                  commonParent.val.get(2)
                ) {
                  maxValue = commonParent.children.get(2).max_value;
                  maxString = commonParent.children.get(2).max_s;
                } else {
                  maxValue = commonParent.val.get(2);
                  maxString = commonParent.s.get(2);
                }
              } else {
                maxValue = commonParent.val.get(2);
                maxString = commonParent.s.get(2);
              }

              if (commonParent.val.get(1) >= maxValue) {
                maxValue = commonParent.val.get(1);
                maxString = commonParent.s.get(1);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(2),
                    maxValue,
                    maxString
                  );
              }
            }
          }
          // s1 is at third index
          if (commonParent.s.get(2).compareTo(s1) == 0) {
            // s2 is fourth child
            maxValue = commonParent.val.get(2);
            maxString = commonParent.s.get(2);
            maxString =
              findMaxS2(s2, commonParent.children.get(3), maxValue, maxString);
          }
        }
      }
      // If S2 is Present
      if (!searchEntry(commonParent, s1) && searchEntry(commonParent, s2)) {
        // Common Parent 2-Node
        if (commonParent.s.size() == 1) {
          maxString = commonParent.s.get(0);
          maxValue = commonParent.val.get(0);
          if (commonParent.children.size() != 0) {
            maxString =
              findMaxS1(s1, commonParent.children.get(0), maxValue, maxString);
          }
        }

        // Common Parent 3-Node
        if (commonParent.s.size() == 2) {
          // s1 is first child
          if (commonParent.s.get(0).compareTo(s2) == 0) {
            maxString = commonParent.s.get(0);
            maxValue = commonParent.val.get(0);
            if (commonParent.children.size() != 0) {
              maxString =
                findMaxS1(
                  s1,
                  commonParent.children.get(0),
                  maxValue,
                  maxString
                );
            }
          }

          if (commonParent.s.get(1).compareTo(s2) == 0) {
            // s1 is first child
            if (commonParent.s.get(0).compareTo(s1) > 0) {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(1).max_value >=
                  commonParent.val.get(1)
                ) {
                  maxString = commonParent.children.get(1).max_s;
                  maxValue = commonParent.children.get(1).max_value;
                } else {
                  maxString = commonParent.s.get(1);
                  maxValue = commonParent.val.get(1);
                }
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxString = commonParent.s.get(0);
                maxValue = commonParent.val.get(0);
              }
              String maxString2 = "";
              int maxValue2 = 0;
              if (commonParent.children.size() != 0) {
                maxString2 =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
                maxValue2 = getVal(maxString2);
              }
              if (maxValue2 >= maxValue) {
                maxString = commonParent.s.get(0);
                maxValue = commonParent.val.get(0);
              }
            }
            // s1 is second child
            else {
              maxString = commonParent.s.get(1);
              maxValue = commonParent.val.get(1);
              maxString =
                findMaxS1(
                  s1,
                  commonParent.children.get(1),
                  maxValue,
                  maxString
                );
            }
          }
        }

        // Common Parent 4-Node
        if (commonParent.s.size() == 3) {
          // s2 at first position
          if (commonParent.s.get(0).compareTo(s2) == 0) {
            // s1 in first child
            maxString = commonParent.s.get(0);
            maxValue = commonParent.val.get(0);
            if (commonParent.children.size() != 0) {
              maxString =
                findMaxS1(
                  s1,
                  commonParent.children.get(0),
                  maxValue,
                  maxString
                );
            }
          }
          // s2 at second position
          if (commonParent.s.get(1).compareTo(s2) == 0) {
            // s1 is first child
            if (commonParent.s.get(0).compareTo(s1) > 0) {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(1).max_value >=
                  commonParent.val.get(1)
                ) {
                  maxString = commonParent.children.get(1).max_s;
                  maxValue = commonParent.children.get(1).max_value;
                } else {
                  maxString = commonParent.s.get(1);
                  maxValue = commonParent.val.get(1);
                }
              } else {
                maxString = commonParent.s.get(1);
                maxValue = commonParent.val.get(1);
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxString = commonParent.s.get(0);
                maxValue = commonParent.val.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
              }
            }
            // s1 is second child
            else {
              maxString = commonParent.children.get(1).max_s;
              maxValue = commonParent.children.get(1).max_value;
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(1),
                    maxValue,
                    maxString
                  );
              }
            }
          }
          if (commonParent.s.get(2).compareTo(s2) == 0) {
            // s1 is first child
            if (commonParent.s.get(0).compareTo(s1) > 0) {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(2).max_value >=
                  commonParent.val.get(2)
                ) {
                  maxString = commonParent.children.get(2).max_s;
                  maxValue = commonParent.children.get(2).max_value;
                } else {
                  maxString = commonParent.s.get(2);
                  maxValue = commonParent.val.get(2);
                }
              } else {
                maxString = commonParent.s.get(2);
                maxValue = commonParent.val.get(2);
              }

              if (commonParent.val.get(1) >= maxValue) {
                maxValue = commonParent.val.get(1);
                maxString = commonParent.s.get(1);
              }

              if (commonParent.children.get(1).max_value >= maxValue) {
                maxString = commonParent.children.get(1).max_s;
                maxValue = commonParent.children.get(1).max_value;
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxString = commonParent.s.get(0);
                maxValue = commonParent.val.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
              }
            } else if (commonParent.s.get(1).compareTo(s1) > 0) {
              if (commonParent.children.size() != 0) {
                if (
                  commonParent.children.get(2).max_value >=
                  commonParent.val.get(2)
                ) {
                  maxString = commonParent.children.get(2).max_s;
                  maxValue = commonParent.children.get(2).max_value;
                } else {
                  maxString = commonParent.s.get(2);
                  maxValue = commonParent.val.get(2);
                }
              } else {
                maxString = commonParent.s.get(2);
                maxValue = commonParent.val.get(2);
              }

              if (commonParent.val.get(1) >= maxValue) {
                maxValue = commonParent.val.get(1);
                maxString = commonParent.s.get(1);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(1),
                    maxValue,
                    maxString
                  );
              }
            } else {
              if (commonParent.s.get(0).compareTo(s1) > 0) {
                maxString = commonParent.s.get(2);
                maxValue = commonParent.val.get(2);

                if (commonParent.children.size() != 0) {
                  maxString =
                    findMaxS1(
                      s1,
                      commonParent.children.get(2),
                      maxValue,
                      maxString
                    );
                }
              }
            }
          }
        }
      }

      if (!searchEntry(commonParent, s1) && !searchEntry(commonParent, s2)) {
        // 2 Node
        if (commonParent.s.size() == 1) {
          if (commonParent.children.size() != 0) {
            maxString =
              findMaxS2(s2, commonParent.children.get(1), maxValue, maxString);
            maxValue = getVal(maxString);
          }

          if (commonParent.val.get(0) >= maxValue) {
            maxString = commonParent.s.get(0);
            maxValue = commonParent.val.get(0);
          }

          if (commonParent.children.size() != 0) {
            maxString =
              findMaxS1(s1, commonParent.children.get(0), maxValue, maxString);
          }
        }
        // 3 Node
        if (commonParent.s.size() == 2) {
          // s1 is first child
          if (commonParent.s.get(0).compareTo(s1) > 0) {
            // s2 is second child
            if (commonParent.s.get(1).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(1),
                    maxValue,
                    maxString
                  );
              }
              maxValue = getVal(maxString);

              if (commonParent.val.get(0) >= maxValue) {
                maxString = commonParent.s.get(0);
                maxValue = commonParent.val.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
              }
            }
            // s2 is third child
            else {
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(2),
                    maxValue,
                    maxString
                  );
                maxValue = getVal(maxString);
              }

              if (commonParent.val.get(1) >= maxValue) {
                maxString = commonParent.s.get(1);
                maxValue = commonParent.val.get(1);
              }

              if (commonParent.children.get(1).max_value >= maxValue) {
                maxValue = commonParent.children.get(1).max_value;
                maxString = commonParent.children.get(1).max_s;
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxValue = commonParent.val.get(0);
                maxString = commonParent.s.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
                maxValue = getVal(maxString);
              }
            }
          }
          // s1 is second child
          else if (commonParent.s.get(1).compareTo(s1) > 0) {
            if (commonParent.children.size() != 0) {
              maxString =
                findMaxS2(
                  s2,
                  commonParent.children.get(2),
                  maxValue,
                  maxString
                );
              maxValue = getVal(maxString);
            }
            if (commonParent.val.get(1) >= maxValue) {
              maxString = commonParent.s.get(1);
              maxValue = commonParent.val.get(1);
            }

            if (commonParent.children.size() != 0) {
              maxString =
                findMaxS1(
                  s1,
                  commonParent.children.get(1),
                  maxValue,
                  maxString
                );
            }
          }
        }
        // 4 Node
        if (commonParent.s.size() == 3) {
          // s1 is first child
          if (commonParent.s.get(0).compareTo(s1) > 0) {
            // s2 is second child
            if (commonParent.s.get(1).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(1),
                    maxValue,
                    maxString
                  );
                maxValue = getVal(maxString);
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxString = commonParent.s.get(0);
                maxValue = commonParent.val.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
              }
            }
            // s2 is third child
            else if (commonParent.s.get(2).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(2),
                    maxValue,
                    maxString
                  );
                maxValue = getVal(maxString);
              }

              if (commonParent.val.get(1) >= maxValue) {
                maxString = commonParent.s.get(1);
                maxValue = commonParent.val.get(1);
              }

              if (commonParent.children.get(1).max_value >= maxValue) {
                maxValue = commonParent.children.get(1).max_value;
                maxString = commonParent.children.get(1).max_s;
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxValue = commonParent.val.get(0);
                maxString = commonParent.s.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
                maxValue = getVal(maxString);
              }
            }
            // s2 if fourt child
            else {
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(3),
                    maxValue,
                    maxString
                  );
                maxValue = getVal(maxString);
              }

              if (commonParent.val.get(2) >= maxValue) {
                maxValue = commonParent.val.get(2);
                maxString = commonParent.s.get(2);
              }

              if (commonParent.children.get(2).max_value >= maxValue) {
                maxValue = commonParent.children.get(2).max_value;
                maxString = commonParent.children.get(2).max_s;
              }

              if (commonParent.val.get(1) >= maxValue) {
                maxValue = commonParent.val.get(1);
                maxString = commonParent.s.get(1);
              }

              if (commonParent.children.get(1).max_value >= maxValue) {
                maxValue = commonParent.children.get(1).max_value;
                maxString = commonParent.children.get(1).max_s;
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxValue = commonParent.val.get(0);
                maxString = commonParent.s.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
              }
            }
          }

          // s1 is second child
          if (commonParent.s.get(1).compareTo(s1) > 0) {
            // s2 is third child
            if (commonParent.s.get(2).compareTo(s2) > 0) {
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(2),
                    maxValue,
                    maxString
                  );
                maxValue = getVal(maxString);
              }

              if (commonParent.val.get(0) >= maxValue) {
                maxValue = commonParent.val.get(0);
                maxString = commonParent.s.get(0);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
              }
            }
            // s2 is fourth child
            else {
              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS2(
                    s2,
                    commonParent.children.get(3),
                    maxValue,
                    maxString
                  );
                maxValue = getVal(maxString);
              }

              if (commonParent.val.get(2) >= maxValue) {
                maxValue = commonParent.val.get(2);
                maxString = commonParent.s.get(2);
              }
              if (commonParent.children.get(2).max_value >= maxValue) {
                maxValue = commonParent.children.get(2).max_value;
                maxString = commonParent.children.get(2).max_s;
              }

              if (commonParent.val.get(1) >= maxValue) {
                maxValue = commonParent.val.get(1);
                maxString = commonParent.s.get(1);
              }

              if (commonParent.children.size() != 0) {
                maxString =
                  findMaxS1(
                    s1,
                    commonParent.children.get(0),
                    maxValue,
                    maxString
                  );
              }
            }
          }
          // s1 is third child
          else if (commonParent.s.get(2).compareTo(s1) > 0) {
            if (commonParent.children.size() != 0) {
              maxString =
                findMaxS2(
                  s2,
                  commonParent.children.get(3),
                  maxValue,
                  maxString
                );
              maxValue = getVal(maxString);
            }

            if (commonParent.val.get(2) >= maxValue) {
              maxValue = commonParent.val.get(2);
              maxString = commonParent.s.get(2);
            }

            if (commonParent.children.size() != 0) {
              maxString =
                findMaxS1(
                  s1,
                  commonParent.children.get(2),
                  maxValue,
                  maxString
                );
            }
          }
        }
      }

      return maxString;
    }
  }

  private int countS1(String s1, TreeNode node) {
    int i = 0;
    while (i < node.s.size()) {
      String stringAtI = node.s.get(i);

      // When I reach S1
      if (stringAtI.compareTo(s1) == 0) {
        // Right Sibilings and Right Children ke Count ko add krlunga

        int totalValue = 0;
        while (i < node.s.size()) {
          totalValue += node.val.get(i);
          if (node.children.size() != 0) {
            totalValue += node.children.get(i + 1).count;
          }
          i++;
        }
        return totalValue;
      }
      //  Call Immediate Left Child
      else if (stringAtI.compareTo(s1) > 0) {
        int totalValue = countS1(s1, node.children.get(i));
        while (i < node.s.size()) {
          totalValue += node.val.get(i);
          if (node.children.size() != 0) {
            totalValue += node.children.get(i + 1).count;
          }
          i++;
        }
        return totalValue;
      } else if (s1.compareTo(stringAtI) > 0) {
        i++;
        if (i == node.s.size()) {
          //  Call Immediate Right Child
          int totalValue = countS1(s1, node.children.get(i));
          return totalValue;
        }
      }
    }
    return 0;
  }

  private int countS2(String s2, TreeNode node) {
    // S2
    int j = 0;
    while (j < node.s.size()) {
      String stringAtI = node.s.get(j);
      // Found S2
      if (stringAtI.compareTo(s2) == 0) {
        int totalValue = 0;
        while (j >= 0) {
          totalValue += node.val.get(j);
          if (node.children.size() != 0) {
            totalValue += node.children.get(j).count;
          }
          j--;
        }
        return totalValue;
      }
      // Not Found S2
      else if (stringAtI.compareTo(s2) > 0) {
        // Find S2 in my Left Childint
        int totalValue = 0;
        totalValue += countS2(s2, node.children.get(j));

        return totalValue;
      } else {
        j++;
        if (j == node.s.size()) {
          int totalValue = 0;
          //  Find S2 in my Right Child

          if (node.children.size() != 0) {
            totalValue = countS2(s2, node.children.get(j));
          }
          j--;
          while (j >= 0) {
            totalValue += node.val.get(j);
            if (node.children.size() != 0) {
              totalValue += node.children.get(j).count;
            }
            j--;
          }
          return totalValue;
        }
      }
    }
    return 0;
  }

  private String findMaxS1(String s1, TreeNode node, int maxV, String maxS) {
    int i = 0;
    while (i < node.s.size()) {
      String stringAtI = node.s.get(i);
      // Found S1
      if (stringAtI.compareTo(s1) == 0) {
        int maxValue = maxV;
        String maxString = maxS;

        while (i < node.s.size()) {
          if (node.val.get(i) > maxValue) {
            maxValue = node.val.get(i);
            maxString = node.s.get(i);
          } else if (node.val.get(i) == maxValue) {
            if (node.s.get(i).compareTo(maxString) < 0) {
              maxString = node.s.get(i);
            }
          }
          if (node.children.size() != 0) {
            if (node.children.get(i).max_value > maxValue) {
              maxValue = node.children.get(i).max_value;
              maxString = node.children.get(i).max_s;
            } else if (node.children.get(i).max_value == maxValue) {
              if (node.children.get(i).max_s.compareTo(maxString) < 0) {
                maxString = node.s.get(i);
              }
            }
          }
          i++;
        }
        return maxString;
      }
      //  Call Immediate Left Child
      else if (stringAtI.compareTo(s1) > 0) {
        int maxValue = maxV;
        String maxString = maxS;
        if (node.children.size() != 0) {
          maxString = findMaxS1(s1, node.children.get(i), maxV, maxS);
        }
        maxValue = getVal(maxString);

        while (i < node.s.size()) {
          if (node.val.get(i) > maxValue) {
            maxValue = node.val.get(i);
            maxString = node.s.get(i);
          } else if (node.val.get(i) == maxValue) {
            if (node.s.get(i).compareTo(maxString) < 0) {
              maxString = node.s.get(i);
            }
          }

          if (node.children.size() != 0) {
            if (node.children.get(i + 1).max_value > maxValue) {
              maxValue = node.children.get(i + 1).max_value;
              maxString = node.children.get(i + 1).max_s;
            } else if (node.children.get(i + 1).max_value == maxValue) {
              if (node.children.get(i + 1).max_s.compareTo(maxString) < 0) {
                maxString = node.s.get(i);
              }
            }
          }

          i++;
        }
        return maxString;
      } else if (s1.compareTo(stringAtI) > 0) {
        i++;
        if (i == node.s.size()) {
          //  Call Immediate Right Child
          String maxString = findMaxS1(s1, node.children.get(i), maxV, maxS);
          // int maxValue = getVal(maxString);

          return maxString;
        }
      }
    }
    return null;
  }

  private String findMaxS2(String s2, TreeNode node, int maxV, String maxS) {
    // S2
    int j = 0;
    while (j < node.s.size()) {
      String stringAtI = node.s.get(j);
      if (stringAtI.compareTo(s2) == 0) {
        // Found S2
        int maxValue = maxV;
        String maxString = maxS;

        while (j >= 0) {
          if (node.val.get(j) > maxValue) {
            maxValue = node.val.get(j);
            maxString = node.s.get(j);
          } else if (node.val.get(j) == maxValue) {
            maxString = node.s.get(j);
          }

          if (node.children.size() != 0) {
            if (node.children.get(j).max_value > maxValue) {
              maxValue = node.children.get(j).max_value;
              maxString = node.children.get(j).max_s;
            }
          }
          j--;
        }
        return maxString;
      } else if (stringAtI.compareTo(s2) > 0) {
        // Find S2 in my Left Child
        String maxString = findMaxS2(s2, node.children.get(j), maxV, maxS);
        int maxValue = getVal(maxString);
        j--;
        while (j >= 0) {
          if (node.val.get(j) > maxValue) {
            maxValue = node.val.get(j);
            maxString = node.s.get(j);
          } else if (node.val.get(j) == maxValue) {
            maxString = node.s.get(j);
          }

          if (node.children.size() != 0) {
            if (node.children.get(j).max_value > maxValue) {
              maxValue = node.children.get(j).max_value;
              maxString = node.children.get(j).max_s;
            } else if (node.children.get(j).max_value == maxValue) {
              maxString = node.children.get(j).max_s;
            }
          }
          j--;
        }
        return maxString;
      } else if (s2.compareTo(stringAtI) > 0) {
        j++;
        if (j == node.s.size()) {
          int maxValue = maxV;
          String maxString = maxS;
          //  Find S2 in my Right Child
          if (node.children.size() != 0) {
            maxString = findMaxS2(s2, node.children.get(j), maxV, maxS);
            maxValue = getVal(maxString);
          }
          j--;
          while (j >= 0) {
            if (node.val.get(j) > maxValue) {
              maxValue = node.val.get(j);
              maxString = node.s.get(j);
            } else if (node.val.get(j) == maxValue) {
              maxString = node.s.get(j);
            }
            if (node.children.size() != 0) {
              if (node.children.get(j).max_value > maxValue) {
                maxValue = node.children.get(j).max_value;
                maxString = node.children.get(j).max_s;
              } else if (node.children.get(j).max_value == maxValue) {
                maxString = node.children.get(j).max_s;
              }
            }
            j--;
          }
          return maxString;
        }
      }
    }
    return null;
  }

  // Helper Functions

  private boolean compareString(String s1, String s2) {
    if (s1.compareTo(s2) >= 0) {
      // If s1 >= s2
      return true;
    } else {
      return false;
    }
  }

  public int getVal(String s) {
    // TO be completed by students
    TreeNode node = getNodeRecursive(root, s);
    int i = 0;
    while (i < node.s.size()) {
      if (node.s.get(i).compareTo(s) == 0) {
        return node.val.get(i);
      }
      i++;
    }

    return 0;
  }

  private boolean searchEntry(TreeNode node, String newString) {
    int i = 0;
    while (i < node.s.size()) {
      if (node.s.get(i).compareTo(newString) == 0) {
        return true;
      }
      i++;
    }
    return false;
  }

  private TreeNode getNodeRecursive(TreeNode node, String newString) {
    if (node == null) {
      return null;
    }
    if (searchEntry(node, newString)) {
      return node;
    }
    int i = 0;
    while (i < node.s.size()) {
      if (i < node.children.size()) {
        if (compareString(node.s.get(i), newString)) {
          return getNodeRecursive(node.children.get(i), newString);
        }
        if (i + 1 == node.s.size()) {
          return getNodeRecursive(node.children.get(i + 1), newString);
        }
      }
      i++;
    }
    return null;
  }

  private TreeNode findCommonParent(String s1, String s2) {
    TreeNode temp1 = getNodeRecursive(root, s1);
    TreeNode temp2 = getNodeRecursive(root, s2);

    TreeNode parNode1 = temp1.parent;
    TreeNode parNode2 = temp2.parent;

    if (temp1 == temp2 || (parNode1 == null && parNode2 == null)) {
      return temp1;
    }
    if (parNode1 == parNode2) {
      return parNode1;
    }
    if (parNode1 == temp2) {
      return parNode1;
    }
    if (parNode2 == temp1) {
      return parNode2;
    }
    int h1 = temp1.height;
    int h2 = temp2.height;

    if (parNode2 == null) {
      while (!(temp2 == parNode1)) {
        parNode1 = parNode1.parent;
        h1++;
      }
      return parNode1;
    }

    if (parNode1 == null) {
      while (!(temp1 == parNode2)) {
        parNode2 = parNode2.parent;
        h2++;
      }
      return parNode2;
    }

    while (!(parNode1 == parNode2)) {
      if (h1 < h2) {
        parNode1 = parNode1.parent;
        h1++;
      } else if (h2 > h1) {
        parNode2 = parNode2.parent;
        h2++;
      } else {
        parNode1 = parNode1.parent;
        h1++;
        parNode2 = parNode2.parent;
        h2++;
      }
    }
    return parNode1;
  }

  private void insertEntry(
    TreeNode node,
    String newString,
    int value,
    int countIncrement
  ) {
    int i = 0;
    while (i < node.s.size()) {
      if (!compareString(node.s.get(i), newString)) {
        i++;
      } else {
        break;
      }
    }
    node.s.add(i, newString);
    node.val.add(i, value);
    node.count = node.count + countIncrement;

    int prevMaxValue = node.max_value;
    String prevMaxS = node.max_s;

    int newValue = node.val.get(i);
    String newS = node.s.get(i);

    // Update Max_Value
    if (prevMaxValue < newValue) {
      node.max_value = newValue;
      node.max_s = newS;
    } else if (prevMaxValue == newValue) {
      if (prevMaxS.compareTo(newS) > 0) {
        node.max_s = newS;
      }
    }

    if (node.parent != null) {
      // Parent Count
      node.parent.count = node.parent.count + countIncrement;

      // Parent Max Value
      int parentMaxValue = node.parent.max_value;
      String parentMaxS = node.parent.max_s;

      if (
        parentMaxValue == prevMaxValue && parentMaxS.compareTo(prevMaxS) == 0
      ) {
        if (prevMaxValue < newValue) {
          node.parent.max_value = newValue;
          node.parent.max_s = newS;
        } else if (prevMaxValue == newValue) {
          if (parentMaxS.compareTo(newS) > 0) {
            node.max_s = newS;
          }
        }
      }
    }
  }

  private void splitAtRoot(TreeNode node) {
    // Create Nodes
    TreeNode parNode = new TreeNode();
    TreeNode firstChild = new TreeNode();
    TreeNode secondChild = new TreeNode();

    // Create First Child
    firstChild.s.add(node.s.get(0));
    firstChild.val.add(node.val.get(0));
    firstChild.parent = parNode;

    // Create Parent
    parNode.s.add(node.s.get(1));
    parNode.val.add(node.val.get(1));

    // Create Second Child
    secondChild.s.add(node.s.get(2));
    secondChild.val.add(node.val.get(2));
    secondChild.s.add(node.s.get(3));
    secondChild.val.add(node.val.get(3));
    secondChild.parent = parNode;

    // Assign Children
    parNode.children.add(firstChild);
    parNode.children.add(secondChild);

    // Updating Child Counts Check
    firstChild.count = firstChild.val.get(0);
    secondChild.count = secondChild.val.get(0) + secondChild.val.get(1);

    // Updating Max
    parNode.max_s = node.max_s;
    parNode.max_value = node.max_value;

    // First Child
    firstChild.max_s = firstChild.s.get(0);
    firstChild.max_value = firstChild.val.get(0);

    // Second Child
    int v1 = secondChild.val.get(0);
    int v2 = secondChild.val.get(1);
    String s1 = secondChild.s.get(0);
    String s2 = secondChild.s.get(1);

    if (v1 > v2) {
      secondChild.max_value = v1;
      secondChild.max_s = s1;
    } else if (v1 < v2) {
      secondChild.max_value = v2;
      secondChild.max_s = s2;
    } else {
      secondChild.max_value = v1;
      secondChild.max_s = compareString(s1, s2) ? s2 : s1;
    }

    if (node.children.size() != 0) {
      TreeNode l1 = node.children.get(0);
      l1.parent = firstChild;
      TreeNode l2 = node.children.get(1);
      l2.parent = firstChild;

      firstChild.children.add(l1);
      firstChild.children.add(l2);

      // Update max for Child 1

      String s3 = firstChild.s.get(0);
      int v3 = firstChild.val.get(0);

      String s4 = "";
      int v4 = 0;

      String s5 = "";
      int v5 = 0;
      String s6 = "";
      int v6 = 0;

      v1 = l1.max_value;
      v2 = l2.max_value;
      s1 = l1.max_s;
      s2 = l2.max_s;

      if (v1 > v2) {
        v4 = v1;
        s4 = s1;
      } else if (v1 < v2) {
        v4 = v2;
        s4 = s2;
      } else {
        v4 = v1;
        s4 = compareString(s1, s2) ? s2 : s1;
      }
      if (v3 > v4) {
        firstChild.max_value = v3;
        firstChild.max_s = s3;
      } else if (v3 < v4) {
        firstChild.max_value = v4;
        firstChild.max_s = s4;
      } else {
        firstChild.max_value = v3;
        firstChild.max_s = compareString(s3, s4) ? s4 : s3;
      }

      firstChild.count =
        firstChild.count +
        firstChild.children.get(0).count +
        firstChild.children.get(1).count;

      TreeNode r1 = node.children.get(2);
      r1.parent = secondChild;
      TreeNode r2 = node.children.get(3);
      r2.parent = secondChild;
      TreeNode r3 = node.children.get(4);
      r3.parent = secondChild;

      // Update max for Child 2

      s4 = secondChild.s.get(0);
      v4 = secondChild.val.get(0);
      s5 = "";
      v5 = 0;
      s6 = "";
      v6 = 0;

      v1 = r1.max_value;
      v2 = r2.max_value;
      v3 = r3.max_value;
      s1 = r1.max_s;
      s2 = r2.max_s;
      s3 = r3.max_s;

      if (v1 > v2) {
        v5 = v1;
        s5 = s1;
      } else if (v1 < v2) {
        v5 = v2;
        s5 = s2;
      } else {
        v5 = v1;
        s5 = compareString(s1, s2) ? s2 : s1;
      }

      if (v3 > v4) {
        v6 = v3;
        s6 = s1;
      } else if (v3 < v4) {
        v6 = v4;
        s6 = s2;
      } else {
        v6 = v3;
        s6 = compareString(s3, s4) ? s4 : s3;
      }

      if (v6 > v5) {
        secondChild.max_value = v6;
        secondChild.max_s = s6;
      } else if (v6 < v5) {
        secondChild.max_value = v5;
        secondChild.max_s = s5;
      } else {
        secondChild.max_value = v6;
        secondChild.max_s = compareString(s6, s5) ? s5 : s6;
      }

      secondChild.children.add(r1);
      secondChild.children.add(r2);
      secondChild.children.add(r3);

      secondChild.count =
        secondChild.count +
        secondChild.children.get(0).count +
        secondChild.children.get(1).count +
        secondChild.children.get(2).count;

      // Updating Heights
      firstChild.height = firstChild.children.get(0).height + 1;
      secondChild.height = secondChild.children.get(0).height + 1;
    }

    // Update Parent Node Count
    parNode.count = parNode.val.get(0) + firstChild.count + secondChild.count;

    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;

    // Parent becomes the Root
    root = parNode;
  }

  private void splitAt2NodeParent(TreeNode node) {
    // Transfer 1st Entry to Parent
    // Added
    TreeNode parNode = node.parent;
    insertEntry(parNode, node.s.get(1), node.val.get(1), 0);

    // Create Nodes
    TreeNode firstChild = new TreeNode();
    TreeNode secondChild = new TreeNode();

    // Create First Child
    firstChild.s.add(node.s.get(0));
    firstChild.val.add(node.val.get(0));

    // Create Second Child
    secondChild.s.add(node.s.get(2));
    secondChild.val.add(node.val.get(2));
    secondChild.s.add(node.s.get(3));
    secondChild.val.add(node.val.get(3));

    // Updating Child Counts Check
    firstChild.count = firstChild.val.get(0);
    secondChild.count = secondChild.val.get(0) + secondChild.val.get(1);

    // First Child
    firstChild.max_s = firstChild.s.get(0);
    firstChild.max_value = firstChild.val.get(0);

    // Second Child
    int v1 = secondChild.val.get(0);
    int v2 = secondChild.val.get(1);
    String s1 = secondChild.s.get(0);
    String s2 = secondChild.s.get(1);

    if (v1 > v2) {
      secondChild.max_value = v1;
      secondChild.max_s = s1;
    } else if (secondChild.val.get(0) < secondChild.val.get(1)) {
      secondChild.max_value = v2;
      secondChild.max_s = s2;
    } else {
      secondChild.max_value = v1;
      secondChild.max_s = compareString(s1, s2) ? s2 : s1;
    }
    // Assigning Parent
    firstChild.parent = parNode;
    secondChild.parent = parNode;

    if (node.children.size() != 0) {
      TreeNode l1 = node.children.get(0);
      l1.parent = firstChild;
      TreeNode l2 = node.children.get(1);
      l2.parent = firstChild;

      firstChild.children.add(l1);
      firstChild.children.add(l2);

      firstChild.count =
        firstChild.count +
        firstChild.children.get(0).count +
        firstChild.children.get(1).count;

      // Update max for Child 1

      String s3 = firstChild.s.get(0);
      int v3 = firstChild.val.get(0);

      String s4 = "";
      int v4 = 0;

      String s5 = "";
      int v5 = 0;
      String s6 = "";
      int v6 = 0;

      v1 = l1.max_value;
      v2 = l2.max_value;
      s1 = l1.max_s;
      s2 = l2.max_s;

      if (v1 > v2) {
        v4 = v1;
        s4 = s1;
      } else if (v1 < v2) {
        v4 = v2;
        s4 = s2;
      } else {
        v4 = v1;
        s4 = compareString(s1, s2) ? s2 : s1;
      }
      if (v3 > v4) {
        firstChild.max_value = v3;
        firstChild.max_s = s3;
      } else if (v3 < v4) {
        firstChild.max_value = v4;
        firstChild.max_s = s4;
      } else {
        firstChild.max_value = v3;
        firstChild.max_s = compareString(s3, s4) ? s4 : s3;
      }

      TreeNode r1 = node.children.get(2);
      r1.parent = secondChild;
      TreeNode r2 = node.children.get(3);
      r2.parent = secondChild;
      TreeNode r3 = node.children.get(4);
      r3.parent = secondChild;

      // Update max for Child 2

      s4 = secondChild.s.get(0);
      v4 = secondChild.val.get(0);
      s5 = "";
      v5 = 0;
      s6 = "";
      v6 = 0;

      v1 = r1.max_value;
      v2 = r2.max_value;
      v3 = r3.max_value;
      s1 = r1.max_s;
      s2 = r2.max_s;
      s3 = r3.max_s;

      if (v1 > v2) {
        v5 = v1;
        s5 = s1;
      } else if (v1 < v2) {
        v5 = v2;
        s5 = s2;
      } else {
        v5 = v1;
        s5 = compareString(s1, s2) ? s2 : s1;
      }

      if (v3 > v4) {
        v6 = v3;
        s6 = s1;
      } else if (v3 < v4) {
        v6 = v4;
        s6 = s2;
      } else {
        v6 = v3;
        s6 = compareString(s3, s4) ? s4 : s3;
      }

      if (v6 > v5) {
        secondChild.max_value = v6;
        secondChild.max_s = s6;
      } else if (v6 < v5) {
        secondChild.max_value = v5;
        secondChild.max_s = s5;
      } else {
        secondChild.max_value = v6;
        secondChild.max_s = compareString(s6, s5) ? s5 : s6;
      }

      secondChild.children.add(r1);
      secondChild.children.add(r2);
      secondChild.children.add(r3);

      secondChild.count =
        secondChild.count +
        secondChild.children.get(0).count +
        secondChild.children.get(1).count +
        secondChild.children.get(2).count;

      firstChild.height = firstChild.children.get(0).height + 1;
      secondChild.height = secondChild.children.get(0).height + 1;
    }

    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;

    // Assign Children
    // If I was Left Child of Parent
    if (parNode.children.get(0) == node) {
      parNode.children.add(0, firstChild);
      parNode.children.set(1, secondChild);
    }
    // If I was Right Child of Parent
    else {
      parNode.children.set(1, firstChild);
      parNode.children.add(secondChild);
    }
  }

  private void splitAt3NodeParent(TreeNode node) {
    //   private void splitAt3NodeParent(TreeNode node, TreeNode parNode) {
    // Transfer 1st Entry to Parent
    // Added
    TreeNode parNode = node.parent;

    insertEntry(parNode, node.s.get(1), node.val.get(1), 0);

    // Create Nodes
    TreeNode firstChild = new TreeNode();
    TreeNode secondChild = new TreeNode();

    // Create First Child
    firstChild.s.add(node.s.get(0));
    firstChild.val.add(node.val.get(0));

    // Create Second Child
    secondChild.s.add(node.s.get(2));
    secondChild.val.add(node.val.get(2));
    secondChild.s.add(node.s.get(3));
    secondChild.val.add(node.val.get(3));

    // Updating Child Counts Check
    firstChild.count = firstChild.val.get(0);
    secondChild.count = secondChild.val.get(0) + secondChild.val.get(1);

    // Assigning Parent
    firstChild.parent = parNode;
    secondChild.parent = parNode;

    // First Child
    firstChild.max_s = firstChild.s.get(0);
    firstChild.max_value = firstChild.val.get(0);

    // Second Child
    int v1 = secondChild.val.get(0);
    int v2 = secondChild.val.get(1);
    String s1 = secondChild.s.get(0);
    String s2 = secondChild.s.get(1);

    if (v1 > v2) {
      secondChild.max_value = v1;
      secondChild.max_s = s1;
    } else if (secondChild.val.get(0) < secondChild.val.get(1)) {
      secondChild.max_value = v2;
      secondChild.max_s = s2;
    } else {
      secondChild.max_value = v1;
      secondChild.max_s = compareString(s1, s2) ? s2 : s1;
    }

    if (node.children.size() != 0) {
      TreeNode l1 = node.children.get(0);
      l1.parent = firstChild;
      TreeNode l2 = node.children.get(1);
      l2.parent = firstChild;

      firstChild.children.add(l1);
      firstChild.children.add(l2);

      // Update max for Child 1

      String s3 = firstChild.s.get(0);
      int v3 = firstChild.val.get(0);

      String s4 = "";
      int v4 = 0;

      String s5 = "";
      int v5 = 0;
      String s6 = "";
      int v6 = 0;

      v1 = l1.max_value;
      v2 = l2.max_value;
      s1 = l1.max_s;
      s2 = l2.max_s;

      if (v1 > v2) {
        v4 = v1;
        s4 = s1;
      } else if (v1 < v2) {
        v4 = v2;
        s4 = s2;
      } else {
        v4 = v1;
        s4 = compareString(s1, s2) ? s2 : s1;
      }
      if (v3 > v4) {
        firstChild.max_value = v3;
        firstChild.max_s = s3;
      } else if (v3 < v4) {
        firstChild.max_value = v4;
        firstChild.max_s = s4;
      } else {
        firstChild.max_value = v3;
        firstChild.max_s = compareString(s3, s4) ? s4 : s3;
      }

      firstChild.count =
        firstChild.count +
        firstChild.children.get(0).count +
        firstChild.children.get(1).count;

      TreeNode r1 = node.children.get(2);
      r1.parent = secondChild;
      TreeNode r2 = node.children.get(3);
      r2.parent = secondChild;
      TreeNode r3 = node.children.get(4);
      r3.parent = secondChild;

      // Update max for Child 2

      s4 = secondChild.s.get(0);
      v4 = secondChild.val.get(0);
      s5 = "";
      v5 = 0;
      s6 = "";
      v6 = 0;

      v1 = r1.max_value;
      v2 = r2.max_value;
      v3 = r3.max_value;
      s1 = r1.max_s;
      s2 = r2.max_s;
      s3 = r3.max_s;

      if (v1 > v2) {
        v5 = v1;
        s5 = s1;
      } else if (v1 < v2) {
        v5 = v2;
        s5 = s2;
      } else {
        v5 = v1;
        s5 = compareString(s1, s2) ? s2 : s1;
      }

      if (v3 > v4) {
        v6 = v3;
        s6 = s1;
      } else if (v3 < v4) {
        v6 = v4;
        s6 = s2;
      } else {
        v6 = v3;
        s6 = compareString(s3, s4) ? s4 : s3;
      }

      if (v6 > v5) {
        secondChild.max_value = v6;
        secondChild.max_s = s6;
      } else if (v6 < v5) {
        secondChild.max_value = v5;
        secondChild.max_s = s5;
      } else {
        secondChild.max_value = v6;
        secondChild.max_s = compareString(s6, s5) ? s5 : s6;
      }

      secondChild.children.add(r1);
      secondChild.children.add(r2);
      secondChild.children.add(r3);

      secondChild.count =
        secondChild.count +
        secondChild.children.get(0).count +
        secondChild.children.get(1).count +
        secondChild.children.get(2).count;

      firstChild.height = firstChild.children.get(0).height + 1;
      secondChild.height = secondChild.children.get(0).height + 1;
    }

    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;
    // Assign Children
    // If I was First Child of Parent
    if (parNode.children.get(0) == node) {
      parNode.children.set(0, firstChild);
      parNode.children.add(1, secondChild);
    }
    // If I was Second Child of Parent
    if (parNode.children.get(1) == node) {
      parNode.children.set(1, firstChild);
      parNode.children.add(2, secondChild);
    }
    // If I was Third Child of Parent
    if (parNode.children.get(2) == node) {
      parNode.children.set(2, firstChild);
      parNode.children.add(secondChild);
    }
  }

  private void splitAt4NodeParent(TreeNode node) {
    // Transfer 1st Entry to Parent
    // Added
    TreeNode parNode = node.parent;
    insertEntry(parNode, node.s.get(1), node.val.get(1), 0);

    // Create Nodes
    TreeNode firstChild = new TreeNode();
    TreeNode secondChild = new TreeNode();

    // Create First Child
    firstChild.s.add(node.s.get(0));
    firstChild.val.add(node.val.get(0));

    // Create Second Child
    secondChild.s.add(node.s.get(2));
    secondChild.val.add(node.val.get(2));
    secondChild.s.add(node.s.get(3));
    secondChild.val.add(node.val.get(3));

    // Updating Child Counts Check
    firstChild.count = firstChild.val.get(0);
    secondChild.count = secondChild.val.get(0) + secondChild.val.get(1);

    // Assigning Parent
    firstChild.parent = parNode;
    secondChild.parent = parNode;

    // First Child
    firstChild.max_s = firstChild.s.get(0);
    firstChild.max_value = firstChild.val.get(0);

    // Second Child
    int v1 = secondChild.val.get(0);
    int v2 = secondChild.val.get(1);
    String s1 = secondChild.s.get(0);
    String s2 = secondChild.s.get(1);

    if (v1 > v2) {
      secondChild.max_value = v1;
      secondChild.max_s = s1;
    } else if (secondChild.val.get(0) < secondChild.val.get(1)) {
      secondChild.max_value = v2;
      secondChild.max_s = s2;
    } else {
      secondChild.max_value = v1;
      secondChild.max_s = compareString(s1, s2) ? s2 : s1;
    }

    if (node.children.size() != 0) {
      TreeNode l1 = node.children.get(0);
      l1.parent = firstChild;
      TreeNode l2 = node.children.get(1);
      l2.parent = firstChild;

      firstChild.children.add(l1);
      firstChild.children.add(l2);

      // Update max for Child 1

      String s3 = firstChild.s.get(0);
      int v3 = firstChild.val.get(0);

      String s4 = "";
      int v4 = 0;

      String s5 = "";
      int v5 = 0;
      String s6 = "";
      int v6 = 0;

      v1 = l1.max_value;
      v2 = l2.max_value;
      s1 = l1.max_s;
      s2 = l2.max_s;

      if (v1 > v2) {
        v4 = v1;
        s4 = s1;
      } else if (v1 < v2) {
        v4 = v2;
        s4 = s2;
      } else {
        v4 = v1;
        s4 = compareString(s1, s2) ? s2 : s1;
      }
      if (v3 > v4) {
        firstChild.max_value = v3;
        firstChild.max_s = s3;
      } else if (v3 < v4) {
        firstChild.max_value = v4;
        firstChild.max_s = s4;
      } else {
        firstChild.max_value = v3;
        firstChild.max_s = compareString(s3, s4) ? s4 : s3;
      }

      firstChild.count =
        firstChild.count +
        firstChild.children.get(0).count +
        firstChild.children.get(1).count;

      TreeNode r1 = node.children.get(2);
      r1.parent = secondChild;
      TreeNode r2 = node.children.get(3);
      r2.parent = secondChild;
      TreeNode r3 = node.children.get(4);
      r3.parent = secondChild;

      // Update max for Child 2

      s4 = secondChild.s.get(0);
      v4 = secondChild.val.get(0);
      s5 = "";
      v5 = 0;
      s6 = "";
      v6 = 0;

      v1 = r1.max_value;
      v2 = r2.max_value;
      v3 = r3.max_value;
      s1 = r1.max_s;
      s2 = r2.max_s;
      s3 = r3.max_s;

      if (v1 > v2) {
        v5 = v1;
        s5 = s1;
      } else if (v1 < v2) {
        v5 = v2;
        s5 = s2;
      } else {
        v5 = v1;
        s5 = compareString(s1, s2) ? s2 : s1;
      }

      if (v3 > v4) {
        v6 = v3;
        s6 = s1;
      } else if (v3 < v4) {
        v6 = v4;
        s6 = s2;
      } else {
        v6 = v3;
        s6 = compareString(s3, s4) ? s4 : s3;
      }

      if (v6 > v5) {
        secondChild.max_value = v6;
        secondChild.max_s = s6;
      } else if (v6 < v5) {
        secondChild.max_value = v5;
        secondChild.max_s = s5;
      } else {
        secondChild.max_value = v6;
        secondChild.max_s = compareString(s6, s5) ? s5 : s6;
      }

      secondChild.children.add(r1);
      secondChild.children.add(r2);
      secondChild.children.add(r3);

      secondChild.count =
        secondChild.count +
        secondChild.children.get(0).count +
        secondChild.children.get(1).count +
        secondChild.children.get(2).count;

      firstChild.height = firstChild.children.get(0).height + 1;
      secondChild.height = secondChild.children.get(0).height + 1;
    }
    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;

    // Assign Children
    // If I was First Child of Parent
    if (parNode.children.get(0) == node) {
      parNode.children.set(0, firstChild);
      parNode.children.add(1, secondChild);
    }
    // If I was Second Child of Parent
    if (parNode.children.get(1) == node) {
      parNode.children.set(1, firstChild);
      parNode.children.add(2, secondChild);
    }
    // If I was Third Child of Parent
    if (parNode.children.get(2) == node) {
      parNode.children.set(2, firstChild);
      parNode.children.add(3, secondChild);
    }
    // If I was Fourth Child of Parent
    if (parNode.children.get(3) == node) {
      parNode.children.set(3, firstChild);
      parNode.children.add(secondChild);
    }
  }

  private void handleOverflow(TreeNode node) {
    //   private void handleOverflow(TreeNode node, TreeNode parNode) {
    TreeNode parNode = node.parent;
    // Case 1 -> No Parent Root Node
    if (node == root || parNode == null) {
      splitAtRoot(node);
      return;
    }
    // Case 2 -> Parent node is originally 2-Node
    if (parNode.s.size() == 1) {
      splitAt2NodeParent(node);
      return;
    }
    // Case 3 -> Parent Node is originally 3-Node
    if (parNode.s.size() == 2) {
      splitAt3NodeParent(node);
      return;
    }
    // Case 4 -> Parent Node is originally 4-Node
    if (parNode.s.size() == 3) {
      splitAt4NodeParent(node);
      return;
    }
  }

  private void insertRecursively(TreeNode node, String newString) {
    if (node.children.size() == 0) {
      insertEntry(node, newString, 1, 1);
      if (node.s.size() == 4) {
        handleOverflow(node);
      }
      return;
    } else {
      int i = 0;
      while (i < node.s.size()) {
        if (compareString(node.s.get(i), newString)) {
          insertRecursively(node.children.get(i), newString);
          break;
        }
        if (i + 1 == node.s.size()) {
          insertRecursively(node.children.get(i + 1), newString);
          break;
        }
        i++;
      }

      if (node != null) {
        if (node.s.size() == 4) {
          handleOverflow(node);
        }
      }
      return;
    }
  }

  private void initalizeRoot(String newString) {
    root = new TreeNode();
    root.s.add(newString);
    root.val.add(1);
    root.parent = null;
    root.count = 1;
    root.max_s = newString;
    root.max_value = 1;
    return;
  }
}
