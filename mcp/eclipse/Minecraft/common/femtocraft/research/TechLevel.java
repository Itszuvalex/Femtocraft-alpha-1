package femtocraft.research;

public enum TechLevel {
		MACRO("macro"), 			//Vanilla level
		MICRO("micro"), 			//1st Tier
		NANO("nano"),  			//2nd Tier
		FEMTO("femto"), 			//3rd Tier
		TEMPORAL("temporal"),		//Specialty Tier 1
		DIMENSIONAL("dimensional");	//Specialty Tier 2
		
		public String key;
		
		TechLevel(String key)
		{
			this.key = key;
		}
		
		public static TechLevel getTech(String key)
		{
			if(key == "macro")
			{
				return MACRO;
			}
			else if (key == "micro")
			{
				return MICRO;
			}
			else if (key == "nano")
			{
				return NANO;
			}
			else if(key == "femto")
			{
				return FEMTO;
			}
			else if(key == "temporal")
			{
				return TEMPORAL;
			}
			else if (key == "dimensional")
			{
				return DIMENSIONAL;
			}
			else
			{
				return null;
			}
		}
		
}
