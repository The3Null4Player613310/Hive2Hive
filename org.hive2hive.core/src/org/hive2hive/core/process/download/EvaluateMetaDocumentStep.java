package org.hive2hive.core.process.download;

import org.hive2hive.core.log.H2HLogger;
import org.hive2hive.core.log.H2HLoggerFactory;
import org.hive2hive.core.model.MetaDocument;
import org.hive2hive.core.model.MetaFile;
import org.hive2hive.core.process.ProcessStep;

public class EvaluateMetaDocumentStep extends ProcessStep {

	private static final H2HLogger logger = H2HLoggerFactory.getLogger(EvaluateMetaDocumentStep.class);

	@Override
	public void start() {
		DownloadFileProcessContext context = (DownloadFileProcessContext) getProcess().getContext();
		MetaDocument metaDocument = context.getMetaDocument();
		if (metaDocument == null) {
			// no meta document found
			logger.error("The meta document could not be found and the download process needs to aborted");
			getProcess().stop("Meta document not found");
		} else {
			MetaFile metaFile = (MetaFile) metaDocument;
			logger.debug("The meta document has been found. Downloading all chunks is the next step.");
			GetFileChunkStep nextStep = new GetFileChunkStep(context.getFile(), metaFile,
					context.getFileManager());
			getProcess().setNextStep(nextStep);
		}
	}

	@Override
	public void rollBack() {
		// nothing to do
		getProcess().nextRollBackStep();
	}

}
