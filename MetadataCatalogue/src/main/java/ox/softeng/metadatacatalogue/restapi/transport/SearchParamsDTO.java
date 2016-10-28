package ox.softeng.metadatacatalogue.restapi.transport;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class SearchParamsDTO {
	String searchTerm;
	Integer offset;
	Integer limit;
	List<String> dtypes;
	
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public List<String> getDtypes() {
		return dtypes;
	}
	public void setDtypes(List<String> dtypes) {
		this.dtypes = new ArrayList<String>();
		for(String str : dtypes)
		{
			if(str.equalsIgnoreCase("DataModel"))
			{
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.Database");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.DataModel");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.DataSet");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.DataStandard");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.Form");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.Message");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.Report");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.Workflow");
			}
			else if(str.equalsIgnoreCase("DataType"))
			{
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.DataType");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.EnumerationType");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.PrimitiveType");
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core.ReferenceType");
			}
			else
			{
				this.dtypes.add("ox.softeng.metadatacatalogue.domain.core." + str);
			}
		}
	}
}
