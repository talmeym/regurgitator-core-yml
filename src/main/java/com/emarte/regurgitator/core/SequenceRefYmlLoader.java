package com.emarte.regurgitator.core;

import java.util.Map;
import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.FILE;
import static com.emarte.regurgitator.core.CoreConfigConstants.ID;
import static com.emarte.regurgitator.core.Log.getLog;

public class SequenceRefYmlLoader implements YmlLoader<Step> {
	private static final Log log = getLog(SequenceRefYmlLoader.class);

	@Override
	public Step load(Yaml yaml, Set<Object> allIds) throws RegurgitatorException {
		log.debug("Loading sequence ref");
		Map values = yaml.getValues();
		Sequence sequence = (Sequence) ConfigurationFile.loadFile((String) values.get(FILE));

		if(values.containsKey(ID)) {
			String newId = (String) values.get(ID);
			log.debug("Repackaged sequence '" + sequence.getId() +"' as '" + newId + "'");
			return new Sequence(newId, sequence);
		}

		log.debug("Using sequence '" + sequence.getId() +"' as is");
		return sequence;
	}
}
