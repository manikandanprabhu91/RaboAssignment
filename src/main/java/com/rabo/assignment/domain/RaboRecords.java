package com.rabo.assignment.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class RaboRecords {
	@XmlElement(name = "record")
	private Records records;

    public Records getRecords ()
    {
        return records;
    }

    public void setRecords (Records records)
    {
        this.records = records;
    }

}
