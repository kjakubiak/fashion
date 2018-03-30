package jakubiakFashionItemsLoader;

public class Item {
	String itName;
	String itSymbol;
	Double itPrice;
	Integer itGroupId;
	Integer itStock;
	Integer it_OnAuctions;
	Integer it_Min;
	Boolean itLinked;
	Integer itReserved;
	Boolean itPrivate;
	String itWWW;
	String itDescr;
	Double itCostPrice;
	Integer itWeight;
	String itProducer;
	Boolean itControlStock;
	Boolean SynchEPPrice;
	
	public String getName()
	{
		return this.itName;
	}
	public void setName(String name)
	{
		this.itName = name;
	}
	
	public String getSymbol()
	{
		return this.itSymbol;
	}
	public void setSymbol(String Symbol)
	{
		this.itSymbol = Symbol;
	}
	
	public Double getCostPrice()
	{
		return this.itCostPrice;
	}
	public void setitCostPrice(Double itCostPrice)
	{
		this.itCostPrice = itCostPrice;
	}
	public String getItName() {
		return itName;
	}
	public void setItName(String itName) {
		this.itName = itName;
	}
	public String getItSymbol() {
		return itSymbol;
	}
	public void setItSymbol(String itSymbol) {
		this.itSymbol = itSymbol;
	}
	public Double getItPrice() {
		return itPrice;
	}
	public void setItPrice(Double itPrice) {
		this.itPrice = itPrice;
	}
	public Integer getItGroupId() {
		return itGroupId;
	}
	public void setItGroupId(Integer itGroupId) {
		this.itGroupId = itGroupId;
	}
	public Integer getItStock() {
		return itStock;
	}
	public void setItStock(Integer itStock) {
		this.itStock = itStock;
	}
	public Integer getIt_OnAuctions() {
		return it_OnAuctions;
	}
	public void setIt_OnAuctions(Integer it_OnAuctions) {
		this.it_OnAuctions = it_OnAuctions;
	}
	public Integer getIt_Min() {
		return it_Min;
	}
	public void setIt_Min(Integer it_Min) {
		this.it_Min = it_Min;
	}
	public Boolean getItLinked() {
		return itLinked;
	}
	public void setItLinked(Boolean itLinked) {
		this.itLinked = itLinked;
	}
	public Integer getItReserved() {
		return itReserved;
	}
	public void setItReserved(Integer itReserved) {
		this.itReserved = itReserved;
	}
	public Boolean getItPrivate() {
		return itPrivate;
	}
	public void setItPrivate(Boolean itPrivate) {
		this.itPrivate = itPrivate;
	}
	public String getItWWW() {
		return itWWW;
	}
	public void setItWWW(String itWWW) {
		this.itWWW = itWWW;
	}
	public String getItDescr() {
		return itDescr;
	}
	public void setItDescr(String itDescr) {
		this.itDescr = itDescr;
	}
	public Double getItCostPrice() {
		return itCostPrice;
	}
	public void setItCostPrice(Double itCostPrice) {
		this.itCostPrice = itCostPrice;
	}
	public Integer getItWeight() {
		return itWeight;
	}
	public void setItWeight(Integer itWeight) {
		this.itWeight = itWeight;
	}
	public String getItProducer() {
		return itProducer;
	}
	public void setItProducer(String itProducer) {
		this.itProducer = itProducer;
	}
	public Boolean getItControlStock() {
		return itControlStock;
	}
	public void setItControlStock(Boolean itControlStock) {
		this.itControlStock = itControlStock;
	}
	public Boolean getSynchEPPrice() {
		return SynchEPPrice;
	}
	public void setSynchEPPrice(Boolean synchEPPrice) {
		SynchEPPrice = synchEPPrice;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [itName=");
		builder.append(itName);
		builder.append(", itSymbol=");
		builder.append(itSymbol);
		builder.append(", itPrice=");
		builder.append(itPrice);
		builder.append(", itGroupId=");
		builder.append(itGroupId);
		builder.append(", itStock=");
		builder.append(itStock);
		builder.append(", it_OnAuctions=");
		builder.append(it_OnAuctions);
		builder.append(", it_Min=");
		builder.append(it_Min);
		builder.append(", itLinked=");
		builder.append(itLinked);
		builder.append(", itReserved=");
		builder.append(itReserved);
		builder.append(", itPrivate=");
		builder.append(itPrivate);
		builder.append(", itWWW=");
		builder.append(itWWW);
		builder.append(", itDescr=");
		builder.append(itDescr);
		builder.append(", itCostPrice=");
		builder.append(itCostPrice);
		builder.append(", itWeight=");
		builder.append(itWeight);
		builder.append(", itProducer=");
		builder.append(itProducer);
		builder.append(", itControlStock=");
		builder.append(itControlStock);
		builder.append(", SynchEPPrice=");
		builder.append(SynchEPPrice);
		builder.append("]");
		return builder.toString();
	}
	
}
