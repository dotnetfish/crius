package cn.cloudartisan.crius.bean;

import java.io.Serializable;

public class Page
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int count;
  public int countPage;
  public int currentPage;
  public int size;
}