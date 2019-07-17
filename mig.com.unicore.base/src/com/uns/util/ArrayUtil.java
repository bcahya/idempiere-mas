/**
 * 
 */
package com.uns.util;


public class ArrayUtil
{
  public static int[] merge(int[] array1, int[] array2)
  {
    int totalSize = 0;
    int array1Size = 0;
    if (array1 != null)
    {
      totalSize = array1.length;
      array1Size = array1.length;
    }
    int array2Size = 0;
    if (array2 != null)
    {
      totalSize += array2.length;
      array2Size = array2.length;
    }
    if (totalSize == 0) {
      return null;
    }
    int[] mergedArray = new int[totalSize];
    if (array1Size > 0) {
      for (int i = 0; i < array1Size; i++) {
        mergedArray[i] = array1[i];
      }
    }
    if (array2Size > 0) {
      for (int i = 0; i < array2Size; i++) {
        mergedArray[(i + array1Size)] = array2[(i + array1Size)];
      }
    }
    return mergedArray;
  }
}
