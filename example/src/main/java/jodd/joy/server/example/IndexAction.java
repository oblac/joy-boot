package jodd.joy.server.example;

import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;

@MadvocAction
public class IndexAction {

	@Action
	public void view() {
		System.out.println("Hello!");
	}

}
