package com.network;

/**
 * Created by bingj on 2017/6/19.
 */

interface A{

}
interface B extends A{

}
class Test{
//    private A a = new A() {
//      new B(){
//
//        }
//    };
    private A[] a1 = new A[]{
      new B(){

      },
      new B() {

      }
    };
}
