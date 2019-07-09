
package com.api.codes;

import com.util.RMT2Exception;
import java.util.ArrayList;

public class GeneralCodeException extends RMT2Exception {
    private static final long serialVersionUID = -4771553368009211076L;

public GeneralCodeException() {
    super();
  }

  public GeneralCodeException(String msg) {
    super(msg);
  }

  public GeneralCodeException(int code) {
    super(code);
  }

  public GeneralCodeException(String msg, int code) {
    super(msg, code);
  }

  public GeneralCodeException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

  public GeneralCodeException(String msg, int code, String objname, String methodname) {
    super(msg, code, objname, methodname);
  }

  public GeneralCodeException(Exception e) {
    super(e);
  }
  
  public GeneralCodeException(String msg, Throwable e) {
      super(msg, e);
    }
}
