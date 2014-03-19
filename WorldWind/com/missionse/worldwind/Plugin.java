package com.missionse.worldwind;


public interface Plugin
{
  public String getPluginName();
  public void initialize(ApplicationTemplate.AppFrame parent);
  public void update(); //Keep this until plug-ins initialize their own threads
  public void hookUpdate(int hashCode);
}
