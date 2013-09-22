package com.android.internal.telephony;  
/*  * Copyright (C) 2007 The Android Open Source Project 
 *  * Licensed under the Apache License, Version 2.0 (the "License"); 
  * you may not use this file except in compliance with the License.  
  * You may obtain a copy of the License at  
  *  *      http://www.apache.org/licenses/LICENSE-2.0  
  *  * Unless required by applicable law or agreed to in writing, software  
  * distributed under the License is distributed on an "AS IS" BASIS,  
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
  * See the License for the specific language governing permissions and  
  * limitations under the License.  
  */  

  /**  
   * Interface used to interact with the phone.  Mostly this is used by the  
   * TelephonyManager class.  A few places are still using this directly.  
   * Please clean them up if possible and use TelephonyManager insteadl.  
   *  * {@hide}  
   */ 
  interface ITelephony {      
  /**      
   * End call or go to the Home screen      *      
   * @return whether it hung up     
   */    
   boolean endCall();      
   
   /**      
   * Answer the currently-ringing call.      
   *      
   * If there's already a current active call, that call will be      
   * automatically put on hold.  If both lines are currently in use, the     
   * current active call will be ended.      *    
   * TODO: provide a flag to let the caller specify what policy to use     
   * if both lines are in use.  (The current behavior is hardwired to     
   * "answer incoming, end ongoing", which is how the CALL button      
   * is specced to behave.)      *      
   * TODO: this should be a oneway call (especially since it's called     
   * directly from the key queue thread).      */     
   void answerRingingCall(); 
} 