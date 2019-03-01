package model.comparators;

import model.commands.Command;
import java.util.Comparator;

/**
 * The CommandComparator orders commands based on the the start time of the command.
 */
public class CommandComparator implements Comparator<Command> {

  /**
   * Compares two commands Returns a positive number if command 1 starts after command 2 Returns a
   * negative number if command 1 start before command 2 Return zero if both commands start at the
   * same time.
   *
   * @param command1 The first command to be compared.
   * @param command2 The second command to be compared.
   * @return An integer representing which Command comes first based on time.
   */
  @Override
  public int compare(Command command1, Command command2) {
    return command1.getStartTime() - command2.getStartTime();
  }
}
