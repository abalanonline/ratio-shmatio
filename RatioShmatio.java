/*
 * Copyright 2020 Aleksei Balan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Arrays;

/**
 * Rational number approximation
 */
public class RatioShmatio {

  // javac RatioShmatio.java
  // cp RatioShmatio.class ~
  // alias ratioshmatio="java -classpath ~ RatioShmatio"

  // java RatioShmatio 701955 1200000 hahahahahahaha
  //    7  /   12  -  0.28%  -  1.955c  -  https://en.wikipedia.org/wiki/Equal_temperament
  //   17  /   29  -  0.21%  -  1.493c  -  https://en.wikipedia.org/wiki/58_equal_temperament
  //   24  /   41  -  0.07%  -  0.484c  -  https://en.wikipedia.org/wiki/41_equal_temperament
  //   31  /   53  -  0.01%  -  0.068c  -  https://en.wikipedia.org/wiki/53_equal_temperament

  public static void main(String[] args) {

    long numerator;
    long denominator;
    try {
      numerator = Long.parseLong(args[0]);
      denominator = Long.parseLong(args[1]);
    } catch (RuntimeException e) {
      System.out.format("Example: java %s 1920 1080%n", Arrays.stream(e.getStackTrace())
          .filter(st -> "main".equals(st.getMethodName())).map(StackTraceElement::getClassName)
          .findAny().orElse("ratio"));
      return;
    }

    System.out.format("%5d  /%5d  -  ratio%n", numerator, denominator);
    double maxError = 0.01; // 1.00%

    for (long iDenominator = 1; iDenominator < denominator; iDenominator++) {
      long iNumerator = (int) Math.round((double) (numerator * iDenominator) / denominator);
      double iterationError = (numerator * iDenominator) > (iNumerator * denominator) ?
          ((double) numerator * iDenominator) / (iNumerator * denominator) - 1 :
          ((double) iNumerator * denominator) / (numerator * iDenominator) - 1;

      // screen dimensions are music too
      double cents = Math.abs((((double) iNumerator / iDenominator) - ((double) numerator / denominator)) * 1200);
      if (iterationError >= maxError) continue;
      maxError = iterationError;
      System.out.format("%5d  /%5d  -  %.2f%%  -  %.3fc%n", iNumerator, iDenominator, iterationError * 100, cents);
      if (iterationError < 0.0001) break;
    }

  }
}
