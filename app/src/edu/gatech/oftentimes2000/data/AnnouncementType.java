package edu.gatech.oftentimes2000.data;

public enum AnnouncementType 
{
	Advertisement
	{
		@Override
		public String toString() 
		{
			return "Advertisement";
		}
	},
	
	PSA
	{
		@Override
		public String toString() 
		{
			return "Public Service Announcement";
		}
	},
	
	Event
	{
		@Override
		public String toString() 
		{
			return "Event";
		}
	},
	
	Unknown
	{
		@Override
		public String toString() 
		{
			return "Unknown";
		}
	}
}
