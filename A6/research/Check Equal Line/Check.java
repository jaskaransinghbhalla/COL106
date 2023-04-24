 private boolean isEqualLine(PowerLine line1, PowerLine line2) {
    return (
      (line1.cityA.equals(line2.cityA) && line1.cityB.equals(line2.cityB)) ||
      (line1.cityA.equals(line2.cityB) && line1.cityB.equals(line2.cityA))
    );
  }