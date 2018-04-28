package com.example.lcc.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * <table cellpadding=5 border="1" cellspacing="0">
 * <tr>
 * <th>Category</th>
 * <th>算法名称</th>
 * <th>最好情况</th>
 * <th>平均情况</th>
 * <th>最坏情况</th>
 * <th>空间复杂度</th>
 * <th>是否稳定</th>
 * </tr>
 * <tr>
 * <td rowspan="2">插入排序<br/>
 * （Insertion Sorts）</td>
 * <td>插入排序<br/>
 * （Insertion Sort）</td>
 * <td>n</td>
 * <td>n<sup>2</sup></td>
 * <td>n<sup>2</sup></td>
 * <td>1</td>
 * <td>稳定</td>
 * </tr>
 * <tr>
 * <td>希尔排序<br/>
 * （Shell Sort）</td>
 * <td>n</td>
 * <td>nlog<sup>2</sup>n</td>
 * <td>nlog<sup>2</sup>n</td>
 * <td>1</td>
 * <td>不稳定</td>
 * </tr>
 * <tr>
 * <td rowspan="4">交换排序<br/>
 * （Exchange Sorts ）</td>
 * <td>快速排序<br/>
 * （Quick Sort）
 * </td>
 * <td>nlogn</td>
 * <td>nlogn</td>
 * <td>n<sup>2</sup></td>
 * <td>logn</td>
 * <td>不稳定</td>
 * </tr>
 * <tr>
 * <td>冒泡排序<br/>
 * （Bubble Sort）</td>
 * <td>n</td>
 * <td>n<sup>2</sup></td>
 * <td>n<sup>2</sup></td>
 * <td>1</td>
 * <td>稳定</td>
 * </tr>
 * <tr>
 * <td>鸡尾酒排序<br/>
 * （Cocktail Sort）
 * </td>
 * <td>n</td>
 * <td>n<sup>2</sup></td>
 * <td>n<sup>2</sup></td>
 * <td>1</td>
 * <td>稳定</td>
 * </tr>
 * <tr>
 * <td>奇偶排序<br/>
 * （Odd-Even Sort）
 * </td>
 * <td>n</td>
 * <td>n<sup>2</sup></td>
 * <td>n<sup>2</sup></td>
 * <td>1</td>
 * <td>稳定</td>
 * </tr>
 * <tr>
 * <td rowspan="2"> 选择排序<br/>
 * （Selection Sorts）</td>
 * <td>选择排序<br/>
 * （Selection Sort）</td>
 * <td>n<sup>2</sup></td>
 * <td>n<sup>2</sup></td>
 * <td>n<sup>2</sup></td>
 * <td>1</td>
 * <td>不稳定</td>
 * </tr>
 * <tr>
 * <td>堆排序<br/>
 * （Heap Sort）
 * </td>
 * <td>nlogn</td>
 * <td>nlogn</td>
 * <td>nlogn</td>
 * <td>1</td>
 * <td>不稳定</td>
 * </tr>
 * <tr>
 * <td>合并排序<br/>
 * （Merge Sorts）</td>
 * <td>奇偶排序<br/>
 * （Merge Sort）</td>
 * <td>n</td>
 * <td>nlogn</td>
 * <td>nlogn</td>
 * <td>n</td>
 * <td>稳定</td>
 * </tr>
 * <tr>
 * <td>混合排序<br/>
 * （Hybrid Sorts）
 * </td>
 * <td>内省排序<br/>
 * （Introspective Sort）</td>
 * <td>nlogn</td>
 * <td>nlogn</td>
 * <td>nlogn</td>
 * <td>logn</td>
 * <td>不稳定</td>
 * </tr>
 * </table>
 * 稳定不稳定的意思是，如果相等的元素不会换位置则是稳定，相等的元素会换位 则不稳定
 * <a href="https://www.cnblogs.com/gaochundong/p/comparison_sorting_algorithms.html">文章链接<a/>
 */
public class SortDemo {


    @Test
    public void testSort() throws Exception {
        int[] unsortArray = {9, 8, 7, 4, 1, 3, 0, 2, 1, 4, 9, 8};
        Collections.shuffle(Arrays.asList(unsortArray));
        System.out.println(Arrays.toString(BubbleSort.sort(unsortArray)));
        Collections.shuffle(Arrays.asList(unsortArray));
        System.out.println(Arrays.toString(BubbleSort.optimizedSort(unsortArray)));
        Collections.shuffle(Arrays.asList(unsortArray));
        System.out.println(Arrays.toString(CocktailSort.sort(unsortArray)));
        Collections.shuffle(Arrays.asList(unsortArray));
        System.out.println(Arrays.toString(CocktailSort.optimizedSort(unsortArray)));
        Collections.shuffle(Arrays.asList(unsortArray));
        System.out.println(Arrays.toString(OddEvenSort.sort(unsortArray)));
        Collections.shuffle(Arrays.asList(unsortArray));
        QuickSort.sort(unsortArray, 0, unsortArray.length - 1);
        System.out.println(Arrays.toString(unsortArray));
        Collections.shuffle(Arrays.asList(unsortArray));
        QuickSort.randomPartition(unsortArray, 0, unsortArray.length - 1);
        System.out.println(Arrays.toString(unsortArray));
    }

    /**
     * 冒泡排序
     * 每轮将最大的转移到最后面
     * 冒泡排序对 n 个元素需要 O(n^2) 的比较次数，且可以原地排序。
     * 冒泡排序仅适用于对于含有较少元素的数列进行排序。
     */
    static class BubbleSort {
        /**
         * 冒泡排序
         * 3 2 5 9 2 6  array
         * 第一轮
         * 2 3 5 9 2 6 比较 [0] > [1] true then swap
         * 2 3 5 9 2 6 比较 [1] > [2] false
         * 2 3 5 9 2 6 比较 [2] > [3] false
         * 2 3 5 2 9 6 比较 [3] > [4] true then swap
         * 2 3 5 2 6 9 比较 [4] > [5] true then swap
         * 第二轮
         * 2 3 5 2 6 9 比较 [0] > [1] false
         * 2 3 5 2 6 9 比较 [1] > [2] false
         * 2 3 2 5 6 9 比较 [2] > [3] true then swap
         * 2 3 2 5 6 9 比较 [3] > [4] true then swap
         * 第三轮
         * 2 3 2 5 6 9 比较 [0] > [1] false
         * 2 2 3 5 6 9 比较 [1] > [2] true then swap
         * 2 2 3 5 6 9 比较 [2] > [3] false
         * 第四轮
         * 2 2 3 5 6 9 比较 [0] > [1] false
         * 2 2 3 5 6 9 比较 [1] > [2] false
         * 第五轮
         * 2 2 3 5 6 9 比较 [0] > [1] false
         *
         * @param array
         * @return
         */
        static int[] sort(int[] array) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length - 1 - i; j++) {
                    if (array[j] > array[j + 1]) {
                        swap(array, j, j + 1);
                    }
                }
            }
            return array;
        }

        /**
         * 优化版冒泡排序
         * exchange 记录每次交换到的角标 如果后面都是比较大 且顺序完好的，下轮再比较就可以跳过这部分，直接从最前面进行比较
         * 如：
         * 3 2 1 7 8 9
         * 第一轮 exchange = 5
         * 2 3 1 7 8 9  [0] > [1] true then swap exchange = 1
         * 2 1 3 7 8 9  [1] > [2] true then swap exchange = 2
         * 2 1 3 7 8 9  [2] > [3] false
         * 2 1 3 7 8 9  [3] > [4] false
         * 2 1 3 7 8 9  [4] > [5] false
         * 第二轮 exchange = 2 所以只需要比较到2既可
         * 2 3 1 7 8 9  [0] > [1] false
         * 2 1 3 7 8 9  [2] > [3] true then swap exchange = 1
         * 第三轮 exchange = 1
         * 1 2 3 7 8 9  [0] > [1] true then swap exchange = 0
         * <p>
         *
         * @param array
         * @return
         */
        static int[] optimizedSort(int[] array) {
            int exchange = array.length - 1;
            while (exchange > 0) {
                int lastExchange = exchange;
                exchange = 0;
                for (int i = 0; i < lastExchange; i++) {
                    if (array[i] > array[i + 1]) {
                        swap(array, i, i + 1);
                        exchange = i;
                    }
                }

            }
            return array;
        }
    }

    /**
     * 鸡尾酒排序，也就是双向冒泡排序（Bidirectional Bubble Sort），
     * 是冒泡排序的一种变形。此算法与冒泡排序的不同处在于排序时是以双向在序列中进行排序。
     * 如果序列中的大部分元素已经排序好时，可以得到比冒泡排序更好的性能。
     * 最差时间复杂度 O(n^2)
     * 平均时间复杂度 O(n^2)
     * 最优时间复杂度 O(n)
     * 最差空间复杂度 О(1)
     */
    static class CocktailSort {
        /**
         * 每次一轮将最大的移动到最后面然后将最小的移动到最前面
         * 例如：
         * 7 8 6 9 2 4
         * 第一轮
         * 7 8 6 9 2 4 [0]>[1]? false
         * 7 6 8 9 2 4 [1]>[2]? true then swap
         * 7 6 8 9 2 4 [2]>[3]? false
         * 7 6 8 2 9 4 [3]>[4]? true then swap
         * 7 6 8 2 4 9 [4]>[5]? true then swap
         * <p>
         * 7 6 8 2 4 9 [3]>[4]? false
         * 7 6 2 8 4 9 [2]>[3]? true then swap
         * 7 2 6 8 4 9 [1]>[2]? true then swap
         * 2 7 6 8 4 9 [0]>[1]? true then swap
         * 第二轮
         * 2 6 7 8 4 9 [1]>[2]? true then swap
         * 2 6 7 8 4 9 [2]>[3]? false
         * 2 6 7 4 8 9 [3]>[4]? true then swap
         * <p>
         * 2 6 4 7 8 9 [2]>[3]? true then swap
         * 2 4 6 7 8 9 [2]>[3]? true then swap
         * 第三轮
         * 无
         *
         * @param array
         */
        static int[] sort(int[] array) {
            for (int i = 0; i < array.length / 2; i++) {
                // move the larger to right side 将大数移动到 右侧
                for (int j = i; j + 1 < array.length - i; j++) {
                    if (array[j] > array[j + 1]) {
                        swap(array, j, j + 1);
                    }
                }
                // move the smaller to left side 将小数移动到左侧
                for (int j = array.length - i - 1; j > i; j--) {
                    if (array[j - 1] > array[j]) {
                        swap(array, j - 1, j);
                    }
                }
            }
            return array;
        }

        /**
         * 优化版
         *
         * @param array
         * @return
         */
        static int[] optimizedSort(int[] array) {

            boolean swapped = false;
            int start = 0;
            int end = array.length - 1;
            do {
                swapped = false;
                // move the larger to right side 将大数移动到 右侧
                for (int i = start; i < end; i++) {
                    if (array[i] > array[i + 1]) {
                        swap(array, i, i + 1);
                        swapped = true;
                    }
                }
                if (!swapped) { //说明数组已经排序好的了
                    break;
                }
                swapped = false;
                end = end - 1; //最后一个元素是最大的了 因此下轮比较不需要考虑这个了
                // move the smaller to left side 将小数移动到左侧
                for (int j = end; j > start; j--) {
                    if (array[j - 1] > array[j]) {
                        swap(array, j - 1, j);
                        swapped = true;
                    }
                }
                start = start + 1; //经过上面的处理 start位置的元素为最小的元素了 因此下轮比较直接从start + 1开始
            } while (swapped);
            return array;
        }
    }

    /**
     * 奇偶排序通过比较数组中相邻的（奇-偶）位置元素，如果该奇偶元素对是错误的顺序（前者大于后者），则交换元素。
     * 然后再针对所有的（偶-奇）位置元素进行比较。如此交替进行下去。
     * 最差时间复杂度 O(n2)
     * 平均时间复杂度 O(n2)
     * 最优时间复杂度 O(n)
     * 最差空间复杂度 О(1)
     */
    static class OddEvenSort {
        static int[] sort(int[] array) {
            for (int i = 0; i < array.length; i++) {
                if (i % 2 > 0) {
                    //偶数
                    for (int j = 2; j < array.length; j += 2) {
                        if (array[j] < array[j - 1]) {
                            swap(array, j, j - 1);
                        }
                    }
                } else {
                    //奇数
                    for (int j = 1; j < array.length; j += 2) {
                        if (array[j] < array[j - 1]) {
                            swap(array, j, j - 1);
                        }
                    }
                }
            }
            return array;
        }
    }

    /**
     * 快速排序使用分治法（Divide-and-Conquer）策略将一个数列分成两个子数列并使用递归来处理。
     * <p>
     * 比如有如下这个 10 个数字，[13, 81, 92, 42, 65, 31, 57, 26, 75, 0]。
     * <p>
     * 随机选择一个数作为中间的元素，例如选择 65。
     * 这样数组就被 65 分成了两部分，左边的都小于 65，右边的都大于 65。
     * 然后分别对左右两边的子数组按照相同的方式进行排序，并最终排序完毕
     */
    static class QuickSort {

        /**
         * 选择一个数作为中心点，然后将数列中的数比它小的放左边 比它大的放右边
         *
         * @param array 需要进行排序的数组
         * @param left  数组最左边元素
         * @param right 数组最右边元素
         */
        static void sort(int[] array, int left, int right) {
            if (!(left < right)) { //如果left == right 说明已不可再分，结束
                return;
            }

            //所有array[pivotIndex] 左边的元素都比它小 右边的元素都比它大
            int pivotIndex = partition(array, left, right);

            //递归处理
            //由于主元 左边 都比它小右边都比它大，因此主元已经处理 最终的位置，不需要参数下轮
            sort(array, left, pivotIndex - 1);
            sort(array, pivotIndex + 1, right);
        }

        /**
         * 选择一个数字 对数列进行分区
         *
         * @param array 需要进行排序的数组
         * @param left  数组最左边元素
         * @param right 数组最右边元素
         * @return 分区后哨兵的角标
         */
        static int partition(int[] array, int left, int right) {
            int pivotIndex = right;
            //哨兵，以它为中心，小的放左边 大的去右边
            int sentinel = array[right];
            //需要处理数组的元素为 left ~ right
            int i = left - 1; //下面是先 ++ 因此这里作 - 1处理
            for (int j = left; j <= right - 1; j++) {
                if (array[j] <= sentinel) { //比哨兵小的与最左边的元素交换
                    i++;
                    swap(array, i, j);
                }
            }
            //经过上面的循环 会得到一个left ~ i 都是比哨兵小的元素 i + 1 ~ right 为比哨兵大的元素
            //这里讲 i + 1与哨兵置换
            swap(array, i + 1, pivotIndex);
            //返回哨兵所在的角标
            return i + 1;
        }

        /**
         * 利用随机值取pivot 进行快速排序
         *
         * @param array
         * @param left
         * @param right
         */
        static void randomQuickSort(int[] array, int left, int right) {
            //这里处理逻辑都一样
            if (!(left < right)) {
                return;
            }

            int pivotIndex = randomPartition(array, left, right);

            randomQuickSort(array, left, pivotIndex - 1);
            randomQuickSort(array, pivotIndex + 1, right);
        }

        static Random random = new Random();

        private static int randomPartition(int[] array, int left, int right) {
            int i = left + random.nextInt(right - left);
            swap(array, i, right); //将通过随机数来选出pivot，并与最右侧的作交换，目的是为了复用上面的partition函数
            return partition(array, left, right);
        }







        // ----------------下面是优化版的快速排序---------------------------
        static void optimizedQuickSort(int [] array, int left , int right){
            if (!(left < right)) {
                return; //结束
            }


        }

        /**
         * 优化版partition的过程
         * |3 2 8 4 (6) 5 2 6 1|  ----step 1
         * ---->        <----
         *
         * 在于每次循环找出主元左半区的最大值
         *
         * @param array
         * @param left
         * @param right
         */
        static void optimizedPartition(int [] array, int left , int right){

        }
    }

    static void swap(int[] array, int i, int j) {
        //如果 是 i = j 使用^交换会出问题
        if (i == j) {
            return;
        }
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }

    public static void main(String[] args) {
        //FIXME 使用 ^ 作交换不能 元素自身与自身交换
        int[] ints = {1};
        swap(ints, 0, 0);
        System.out.println(Arrays.toString(ints));
    }

}
