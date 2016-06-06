package cn.yyx.contentassist.commonutils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class ProposalHelper {
	
	private static Image image = null;
	
	static {
		InputStream stream = ProposalHelper.class.getClassLoader().getResourceAsStream("/cn/yyx/contentassist/commonutils/cc.png");
		ImageData id = new ImageData(stream);
		image = new Image(null, id);
		// image = ImageIO.read(new File("icons/cc.png"));
	}
	
	public static void ProposalContentToFormalFormat(JavaContentAssistInvocationContext context, List<String> proposalcnt, List<ICompletionProposal> proposals)
	{
		Iterator<String> itr = proposalcnt.iterator();
		while (itr.hasNext())
		{
			String pol = itr.next();
			proposals.add(new CompletionProposal(pol, 0, pol.length(), 0, image, pol.replace('\n', ' '), null, null));
			
			// (pol, context.getInvocationOffset(), 0, pol.length())
		}
	}
	
}