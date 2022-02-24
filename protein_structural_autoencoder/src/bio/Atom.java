package bio;


import org.joml.Vector3f;

import display.event.Callback;
import display.event.Manager;
import display.eventtypes.AtomLoadEvent;
import display.internal.Color;
import display.internal.CylinderGeometry;
import display.internal.Material;
import display.internal.Mesh;
import display.internal.QuaternionHelper;
import display.internal.SphereGeometry;

public class Atom {
	
	
	public enum Element {
	    
	    H(1,"Hydrogen",1.0080,Color.White),
	    He(2,"Helium",4.0026,Color.Cyan),
	    Li(3,"Lithium",6.9410,Color.Purple),
	    Be(4,"Beryllium",9.0122,Color.DarkGreen),
	    B(5,"Boron",10.8100),
	    C(6,"Carbon",12.0110,Color.Grey),
	    N(7,"Nitrogen",14.0067,Color.Blue),
	    O(8,"Oxygen",15.9994,Color.Red),
	    F(9,"Fluorine",18.9984,Color.Green),
	    Ne(10,"Neon",20.1790,Color.Cyan),
	    Na(11,"Sodium",22.9898,Color.Purple),
	    Mg(12,"Magnesium",24.3050,Color.DarkGreen),
	    Al(13,"Aluminum",26.9815),
	    Si(14,"Silicon",28.0855),
	    P(15,"Phosphorus",30.9738,Color.Orange),
	    S(16,"Sulfur",32.0600,Color.Yellow),
	    Cl(17,"Chlorine",35.4530),
	    K(19,"Potassium",39.0983,Color.Purple),
	    Ar(18,"Argon",39.9480,Color.Cyan),
	    Ca(20,"Calcium",40.0800,Color.DarkGreen),
	    Sc(21,"Scandium",44.9559),
	    Ti(22,"Titanium",47.9000,Color.DarkGrey),
	    V(23,"Vanadium",50.9415),
	    Cr(24,"Chromium",51.9960),
	    Mn(25,"Manganese",54.9380),
	    Fe(26,"Iron",55.8470,Color.DarkOrange),
	    Ni(28,"Nickel",58.7000),
	    Co(27,"Cobalt",58.9332),
	    Cu(29,"Copper",63.5460),
	    Zn(30,"Zinc",65.3800),
	    Ga(31,"Gallium",69.7200),
	    Ge(32,"Germanium",72.5900),
	    As(33,"Arsenic",74.9216),
	    Se(34,"Selenium",78.9600),
	    Br(35,"Bromine",79.9040,Color.DarkRed),
	    Kr(36,"Krypton",83.8000,Color.Cyan),
	    Rb(37,"Rubidium",85.4678,Color.Purple),
	    Sr(38,"Strontium",87.6200,Color.DarkGreen),
	    Y(39,"Yttrium",88.9059),
	    Zr(40,"Zirconium",91.2200),
	    Nb(41,"Niobium",92.9064),
	    Mo(42,"Molybdenum",95.9400),
	    Tc(43,"Technetium",98.0000),
	    Ru(44,"Ruthenium",101.0700),
	    Rh(45,"Rhodium",102.9055),
	    Pd(46,"Palladium",106.4000),
	    Ag(47,"Silver",107.8680),
	    Cd(48,"Cadmium",112.4100),
	    In(49,"Indium",114.8200),
	    Sn(50,"Tin",118.6900),
	    Sb(51,"Antimony",121.7500),
	    I(53,"Iodine",126.9045,Color.DarkPurple),
	    Te(52,"Tellurium",127.6000),
	    Xe(54,"Xenon",131.3000),
	    Cs(55,"Cesium",132.9054,Color.Purple),
	    Ba(56,"Barium",137.3300,Color.DarkGreen),
	    La(57,"Lanthanum",138.9055),
	    Ce(58,"Cerium",140.1200),
	    Pr(59,"Praseodymium",140.9077),
	    Nd(60,"Neodymium",144.2400),
	    Pm(61,"Promethium",145.0000),
	    Sm(62,"Samarium",150.4000),
	    Eu(63,"Europium",151.9600),
	    Gd(64,"Gadolinium",157.2500),
	    Tb(65,"Terbium",158.9254),
	    Dy(66,"Dysprosium",162.5000),
	    Ho(67,"Holmium",164.9304),
	    Er(68,"Erbium",167.2600),
	    Tm(69,"Thulium",168.9342),
	    Yb(70,"Ytterbium",173.0400),
	    Lu(71,"Lutetium",174.9670),
	    Hf(72,"Hafnium",178.4900),
	    Ta(73,"Tantalum",180.9479),
	    W(74,"Tungsten",183.8500),
	    Re(75,"Rhenium",186.2070),
	    Os(76,"Osmium",190.2000),
	    Ir(77,"Iridium",192.2200),
	    Pt(78,"Platinum",195.0900),
	    Au(79,"Gold",196.9665),
	    Hg(80,"Mercury",200.5900),
	    Tl(81,"Thallium",204.3700),
	    Pb(82,"Lead",207.2000),
	    Bi(83,"Bismuth",208.9804),
	    Po(84,"Polonium",209.0000),
	    At(85,"Astatine",210.0000),
	    Rn(86,"Radon",222.0000),
	    Fr(87,"Francium",223.0000,Color.Purple),
	    Ra(88,"Radium",226.0254,Color.DarkGreen),
	    Ac(89,"Actinium",227.0278),
	    Pa(91,"Protactinium",231.0359),
	    Th(90,"Thorium",232.0381),
	    Np(93,"Neptunium",237.0482),
	    U(92,"Uranium",238.0290);
	    
	    private int atomicNumber;
	    private String name;
	    private double atomicWeight;
	    private Color color; // CPK Color
	    
	    private Element(int atomicNumber, String name, double atomicWeight, Color color) {
	        this.atomicNumber = atomicNumber;
	        this.name = name;
	        this.atomicWeight = atomicWeight;
	        this.color = new Color(color);
	    }
	    
	    private Element(int atomicNumber, String name, double atomicWeight) {
	        this.atomicNumber = atomicNumber;
	        this.name = name;
	        this.atomicWeight = atomicWeight;
	        this.color = new Color(Color.Magenta);
	    }

	    public int getAtomicNumber() {
	        return atomicNumber;
	    }

	    public String getName() {
	        return name;
	    }

	    public double getAtomicWeight() {
	        return atomicWeight;
	    }
	    
	    public Color getColor() {
	    	return color;
	    }
	}
	
	private static SphereGeometry sphereGeometry = new SphereGeometry(0.5f, 8, 16);
	
	private static Manager eventManager = new Manager();
	public static void setBindingOnLoad(Callback c) {
		eventManager.register(AtomLoadEvent.Name, c);
	}

	private Vector3f position;
	private String type;
	private int residueID;
	private Element element;
	private final Mesh mesh;
	
	public Atom(String type, Vector3f position, int residueID) {
		this.type = type.trim();
		this.position = position;
		this.residueID = residueID;
		this.element = Element.valueOf(type.trim().substring(0,1)); 
        this.mesh = new Mesh(sphereGeometry, new Material(this.element.getColor()));
        this.mesh.setPosition(this.position);
	}
	
	static Atom load(String data) {
		bio.pdb.records.Atom record = new bio.pdb.records.Atom(bio.pdb.Record.ATOM.parse(data));
		Vector3f pos = new Vector3f(record.x,record.y,record.z);
		//System.out.println("type :"+record.name+", pos: "+(pos.x+","+pos.y+","+pos.z)+", reqSeq: "+record.resSeq);
		eventManager.fire(new AtomLoadEvent().setData(record));
		return new Atom(record.name,pos,record.resSeq);
	}
	
	public Element getElement() {
		return element;
	}
	
	@Deprecated
	public Mesh getMesh() {
		return mesh;
	}
	
	public Mesh buildMesh() {
		Mesh mesh = new Mesh(sphereGeometry, new Material(this.element.getColor()));
        mesh.setPosition(this.position);
        return mesh;
	}
	
	public Color getColor() {
		return mesh.getColor();
	}

	public Vector3f getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}
	
	public boolean isAlphaCarbon() {
		return type.length() >= 2 && type.substring(0,2).equals("CA");
	}
	
	public int getResidueID() {
		return residueID;
	}
	
	public float AtomDistance(Atom a) {
		float x = (float) Math.pow(this.position.x - a.position.x,2);
		float y = (float) Math.pow(this.position.y - a.position.y,2);
		float z = (float) Math.pow(this.position.z - a.position.z,2);
		return (float) Math.sqrt(x+y+z);
	}
	
	
	public static Mesh buildMeshConnection(Atom start, Atom end) {
		Vector3f p1 = start.getPosition();
		Vector3f p2 = end.getPosition();
		float dist = p1.distance(p2);
		Vector3f pos = new Vector3f();
		p1.add(p2,pos);pos.div(2.0f);
		CylinderGeometry cylinderGeo2 = new CylinderGeometry(0.3f,dist);
		Mesh cylinder = new Mesh(cylinderGeo2, new Material(Color.Magenta));
		cylinder.getTransform().setInternalRotation(QuaternionHelper.getRotationBetween(p1,p2));
		cylinder.setPosition(pos);
		return cylinder;
	}
	
}
