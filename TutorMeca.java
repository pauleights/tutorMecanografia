import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.Mixer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TutorMeca extends JFrame {
	
	private final TutorMeca miVentana = this;
	
	private JLabel instrucciones1, instrucciones2;
	
	private static JTextArea areaTexto;
	
	private JButton[] tecladoBotonesPrimerLinea,//botones del teclado correspondientes a la primera linea 
					  tecladoBotonesSegundaLinea,//botones del teclado correspondientes a la segunda linea 
					  tecladoBotonesTercerLinea, //botones del teclado correspondientes a la tercera linea
					  tecladoBotonesCuartaLinea, //botones del teclado correspondientes a la cuarta linea
					  tecladoBotonesQuintaLinea;//botones del teclado correspondientes a la quinta linea
	
	private JButton botonPangramas;
	
	//panel norte que va alojar  las instrucciones
	private JPanel panelNorte;
	//panel sur que vaa alojar todo el teclado
	private JPanel panelSur;
	//paneles para poder acomodar las lineas delos botones
	private JPanel panelTecladoPrimerLinea, 
				   panelTecladoSegundaLinea, 
				   panelTecladoTercerLinea, 
				   panelTecladoCuartaLinea, 
				   panelTecladoQuintaLinea;
	
	private static final int CORCHETE_ABIERTO = 161;
	private static final int CORCHETE_CERRADO = 162;
	private static final int APOSTROFE = 222;
	private static final int LETRA_ENIE = 0;
	
	private static int contadorCaracteres =  0;

	
	private boolean usuarioEscribe = true;
	
	private AudioInputStream archivoSonido;
	private Clip clip;
	
	private static final int PANGRAMA_0 = 0;
	private static final int PANGRAMA_1 = 1;
	private static final int PANGRAMA_2 = 2;
	private static final int PANGRAMA_3 = 3;
	private static final int PANGRAMA_4 = 4;
	private static final int PANGRAMA_5 = 5;
	private static final int PANGRAMA_6 = 6;
	private static final int PANGRAMA_7 = 7;
	private static final int PANGRAMA_8 = 8;
	private static final int PANGRAMA_9 = 9;
	private static final int PANGRAMA_10 = 10;
	private static final int PANGRAMA_11 = 11;
	private static final int PANGRAMA_12 = 12;
	private static final int PANGRAMA_13 = 13;
	private static final int PANGRAMA_14 = 14;
	private static final int PANGRAMA_15 = 15;
	
	//PENDIENTE POR IMPLEMENTARP POR  QUE SERIAN MUCHAS VARIABLES 
	private int[] erroresPangrama;
	private int[] aciertosPangrama;
	
	
	//ARREGLO DE CHARS QUE ALMACENARA LOS CARACTERES DE ERROES
	char[][] caracteresErrores = new char[16][100];
	
	private int pasosDelJuego = 0;

	//campos del area de pangramas
	private static JTextArea areaPangramas;
	private JLabel etiquetaAciertos, etiquetaAciertos2;
	private JPanel panelColorAcierto, panelColorAcierto2;
	
	private boolean terminoPangrama;
	private boolean demasiadosErrores;
	
	//revisar si se necesitan
	private String[] arregloPangramasString = { 
			"El veloz murciélago hindú comía feliz cardillo y kiwi",
			"La cigüeña tocaba el saxofón detrás del palenque de paja",
			"Aquel inexpresivo niño trabajaba mezclando whisky en garrafa",
			"Aquel biografo se zampo un extraño sandwich de vodka y ajo",
			"El alambique de la granja extremeña vaporiza whisky de chufa",
			"El extraño whisky quemo como fuego la boca del joven Lopez",
			"Jovencillo emponzoñado de whisky: ¡qué figurota exhibe!",
			"Queda gazpacho, fibra, látex, jamón, kiwi y viñas",
			"Chaikovsky bajeó exitos del pequeño Wolfgang Mozart",
			"La vieja cigüeña fóbica quiso empezar hoy un éxodo a Kuwait",
			"El mejor fue Karl von Weber, quien ya gozó del éxito por años en China",
			"Benjamín pidió una bebida de kiwi y fresa. Noé, sin vergüenza, la más exquisita champaña del menú",
			"José compró en Perú una vieja zampoña. Excusándose, Sofía tiró su whisky al desagüe de la banqueta",
			"El cadáver de Wamba, rey godo de España, fue exhumado y trasladado en una caja de zinc que pesó un kilo",
			"El baño de wolframio de un equipo de rayos X es capaz de generar unas horas de kilovoltaje",
			"Hoy bajó su valor la wulfenita, extraño molibdato que se cotiza por kilogramo"
				
			
	};
	
	//private static boolean borrarPantalla = false;
	
	public TutorMeca (){
		
		
		super( "Tutor de mecanorafia" );
		
		//INICILIZA ARREGLOS////////////////////////////////////////////////////////
		erroresPangrama = new int [16];
		//for ( int a = 0; a < erroresPangrama.length; a++ )
			// erroresPangrama[a] = 0;
		aciertosPangrama = new int [16];
		//for ( int b = 0; b < aciertosPangrama.length; b++)
			// aciertosPangrama[b] = 0;
		
		
		
		
		
		//PARA VER DE DONDE SE OBTIENE EL SONIDO/////////////////////////////////////////////
		
		Mixer mixer = AudioSystem.getMixer(null);
		Info[] infoLines = mixer.getTargetLineInfo();
		
		//System.out.println(mixer);
		//System.out.println(infoLines);
		
		//CODIGO PARA EL PANEL NORTE/////////////////////////////////////////////////////////
		
		
		panelNorte = new JPanel ();
		panelNorte.setLayout( new FlowLayout() );
		panelNorte.setBackground( Color.GRAY );
		
		JPanel subPanelNorte = new JPanel();
		subPanelNorte.setLayout( new GridLayout(2, 1) );
		subPanelNorte.setBackground( Color.GRAY );
		
		
		instrucciones1 = new JLabel ("Escribe algo de texto usando el teclado. Las teclas que presiones seran resaltadas y el texto sera mostrado");
		instrucciones1.setForeground( Color.BLACK );
		instrucciones2 = new JLabel ("Nota: Dar clic a los botones con el raton no hara niguna accion");
		instrucciones2.setForeground( Color.BLACK );
		
		subPanelNorte.add(instrucciones1);
		subPanelNorte.add(instrucciones2);
		
		panelNorte.add(subPanelNorte);
		
		
		add( panelNorte, BorderLayout.NORTH );
		
		
		areaTexto = new JTextArea ();
		areaTexto.setLineWrap(true);
				
		add( new JScrollPane( areaTexto ), BorderLayout.CENTER );
		
		//COdIGO PARA EL BOTON DE PANGRAMAS////////////////////////////////////////////
		
		//Se crea objeto  que con tiene la ventana de los pangramas
		Pangramas ventanaPangramas = new Pangramas ();
		ventanaPangramas.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
		ventanaPangramas.setSize(600, 200);
		ventanaPangramas.setAlwaysOnTop(true);
		ventanaPangramas.setResizable(false);
		
		//INTENTO DE PERSONALIZAR LAS VENANAS EMERGENTES (PENDIENTE)
		JOptionPane ventanaMensajes = new JOptionPane ("Pangrama definicion:\nFrase que contiene todas las letras del alfabeto"
				+ " por lo menos una vez");
		ventanaMensajes.setBackground( Color.GRAY );
		ventanaMensajes.setForeground(Color.BLACK);
		
		
		botonPangramas = new JButton ("Click  aquí  para ver Pangramas");
		botonPangramas.setBackground(Color.LIGHT_GRAY);
		botonPangramas.setToolTipText("Trata de escribir algunos pangramas en la pantalla");
		botonPangramas.addActionListener(
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						JOptionPane.showMessageDialog(ventanaPangramas, "Pangramas definicion:\nFrase que contiene todas las letras del alfabeto"
								+ " por lo menos una vez");
						
						///PENDIENTE
						//ventanaMensajes.createDialog(ventanaPangramas, "Pangramas definicion:\nFrase que contiene todas las letras del alfabeto"
						//		+ " por lo menos una vez");
						
						ventanaPangramas.setLocation(400, 0);//la mueve a la parte superior
						ventanaPangramas.setVisible(true);//muestra la ventanaa
						
						
						
					}//fin de metodo actionPerfirmes
					
					
				});//fin de metodo addActionListener
		
		//CODIGO PARA EL AREA DE PANGRAMAS
		JPanel panelPangramas = new JPanel();
		panelPangramas.setLayout( new FlowLayout() );
		panelPangramas.setBackground( Color.GRAY );
		
		panelColorAcierto = new JPanel();
		panelColorAcierto2 = new JPanel();
		
		etiquetaAciertos = new JLabel();
		etiquetaAciertos2 = new JLabel();
		
		areaPangramas = new JTextArea();
		areaPangramas.setFont( new Font( "SANS_SERIF", Font.BOLD,  12) );
		areaPangramas.setEditable(false);
		areaPangramas.setBackground( Color.GRAY );
		areaPangramas.setSelectedTextColor( Color.ORANGE );
		
		panelPangramas.add(panelColorAcierto);
		panelPangramas.add(etiquetaAciertos);
		panelPangramas.add(areaPangramas);
		panelPangramas.add(etiquetaAciertos2);
		panelPangramas.add(panelColorAcierto2);
		
		//CODIGO PRIMERA LINEA DEL TECLADO////////////////////////////////////////////
		
		panelTecladoPrimerLinea = new JPanel();
		panelTecladoPrimerLinea.setLayout( new FlowLayout() );
		panelTecladoPrimerLinea.setBackground( Color.GRAY );
				
		//inicializa arreglo de botones
		tecladoBotonesPrimerLinea = new JButton [14];
		tecladoBotonesPrimerLinea[0] = new JButton ("|");
		tecladoBotonesPrimerLinea[0].setBackground( Color.DARK_GRAY );
		tecladoBotonesPrimerLinea[0].setForeground( Color.LIGHT_GRAY );
		for ( int i = 1 ; i <= 9; i++){
			tecladoBotonesPrimerLinea[i] =new JButton(""+i);
			tecladoBotonesPrimerLinea[i].setBackground( Color.DARK_GRAY );
			tecladoBotonesPrimerLinea[i].setForeground( Color.LIGHT_GRAY );
		}
			
		tecladoBotonesPrimerLinea[10] = new JButton("0");
		tecladoBotonesPrimerLinea[10].setBackground( Color.DARK_GRAY );
		tecladoBotonesPrimerLinea[10].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesPrimerLinea[11] = new JButton("'");
		tecladoBotonesPrimerLinea[11].setBackground( Color.DARK_GRAY );
		tecladoBotonesPrimerLinea[11].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesPrimerLinea[12] = new JButton("¿");
		tecladoBotonesPrimerLinea[12].setBackground( Color.DARK_GRAY );
		tecladoBotonesPrimerLinea[12].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesPrimerLinea[13] = new JButton("<--------");
		tecladoBotonesPrimerLinea[13].setBackground( Color.DARK_GRAY );
		tecladoBotonesPrimerLinea[13].setForeground( Color.LIGHT_GRAY );
		
		//se agregan botones al panel
		for ( int j = 0; j < 14; j++)
			panelTecladoPrimerLinea.add(tecladoBotonesPrimerLinea[j]);
		
		
		//CODIGO SEGUNDA LINEA DEL TECLADO////////////////////////////////////////////
				
		panelTecladoSegundaLinea = new JPanel ();
		panelTecladoSegundaLinea.setLayout( new FlowLayout() );
		panelTecladoSegundaLinea.setBackground( Color.GRAY );
		
		//inicializa botones
		tecladoBotonesSegundaLinea = new JButton [14];
		tecladoBotonesSegundaLinea[0] = new JButton ("Tab");
		tecladoBotonesSegundaLinea[0].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[0].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[1] = new JButton ("Q");
		tecladoBotonesSegundaLinea[1].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[1].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[2] = new JButton ("W");
		tecladoBotonesSegundaLinea[2].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[2].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[3] = new JButton ("E");
		tecladoBotonesSegundaLinea[3].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[3].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[4] = new JButton ("R");
		tecladoBotonesSegundaLinea[4].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[4].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[5] = new JButton ("T");
		tecladoBotonesSegundaLinea[5].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[5].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[6] = new JButton ("Y");
		tecladoBotonesSegundaLinea[6].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[6].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[7] = new JButton ("U");
		tecladoBotonesSegundaLinea[7].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[7].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[8] = new JButton ("I");
		tecladoBotonesSegundaLinea[8].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[8].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[9] = new JButton ("O");
		tecladoBotonesSegundaLinea[9].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[9].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[10] = new JButton ("P");
		tecladoBotonesSegundaLinea[10].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[10].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[11] = new JButton ("´");
		tecladoBotonesSegundaLinea[11].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[11].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[12] = new JButton ("+");
		tecladoBotonesSegundaLinea[12].setBackground( Color.DARK_GRAY );
		tecladoBotonesSegundaLinea[12].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesSegundaLinea[13] = new JButton ("");// no se utiliza
		
		//se agregan botones al panel
		for( int k = 0; k < 13; k++ )
			panelTecladoSegundaLinea.add(tecladoBotonesSegundaLinea[k]);
		
		
		//CODIGO TERCER LINEA DEL TECLADO////////////////////////////////////////////
		
		panelTecladoTercerLinea = new JPanel ();
		panelTecladoTercerLinea.setLayout( new FlowLayout() );
		panelTecladoTercerLinea.setBackground( Color.GRAY );
		
		//inicializa botones
		tecladoBotonesTercerLinea = new JButton [14];
		
		tecladoBotonesTercerLinea[0] = new JButton ("Mayus");
		tecladoBotonesTercerLinea[0].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[0].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[1] = new JButton ("A");
		tecladoBotonesTercerLinea[1].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[1].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[2] = new JButton ("S");
		tecladoBotonesTercerLinea[2].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[2].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[3] = new JButton ("D");
		tecladoBotonesTercerLinea[3].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[3].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[4] = new JButton ("F");
		tecladoBotonesTercerLinea[4].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[4].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[5] = new JButton ("G");
		tecladoBotonesTercerLinea[5].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[5].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[6] = new JButton ("H");
		tecladoBotonesTercerLinea[6].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[6].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[7] = new JButton ("J");
		tecladoBotonesTercerLinea[7].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[7].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[8] = new JButton ("K");
		tecladoBotonesTercerLinea[8].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[8].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[9] = new JButton ("L");
		tecladoBotonesTercerLinea[9].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[9].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[10] = new JButton ("Ñ");
		tecladoBotonesTercerLinea[10].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[10].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[11] = new JButton ("{");
		tecladoBotonesTercerLinea[11].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[11].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[12] = new JButton ("}");
		tecladoBotonesTercerLinea[12].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[12].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesTercerLinea[13] = new JButton ("Enter");
		tecladoBotonesTercerLinea[13].setBackground( Color.DARK_GRAY );
		tecladoBotonesTercerLinea[13].setForeground( Color.LIGHT_GRAY );
		
		//se agregan botones al panel
		for( int k = 0; k < 14; k++ )
			panelTecladoTercerLinea.add(tecladoBotonesTercerLinea[k]);
				
		
		//CODIGO CUARTA LINEA DEL TECLADO////////////////////////////////////////////
		
		panelTecladoCuartaLinea = new JPanel ();
		panelTecladoCuartaLinea.setLayout( new FlowLayout() );
		panelTecladoCuartaLinea.setBackground( Color.GRAY );
				
		//inicializa botones
		tecladoBotonesCuartaLinea = new JButton [13];
				
		tecladoBotonesCuartaLinea[0] = new JButton ("Shift");
		tecladoBotonesCuartaLinea[0].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[0].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[1] = new JButton ("Z");
		tecladoBotonesCuartaLinea[1].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[1].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[2] = new JButton ("X");
		tecladoBotonesCuartaLinea[2].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[2].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[3] = new JButton ("C");
		tecladoBotonesCuartaLinea[3].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[3].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[4] = new JButton ("V");
		tecladoBotonesCuartaLinea[4].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[4].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[5] = new JButton ("B");
		tecladoBotonesCuartaLinea[5].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[5].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[6] = new JButton ("N");
		tecladoBotonesCuartaLinea[6].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[6].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[7] = new JButton ("M");
		tecladoBotonesCuartaLinea[7].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[7].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[8] = new JButton (",");
		tecladoBotonesCuartaLinea[8].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[8].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[9] = new JButton (".");
		tecladoBotonesCuartaLinea[9].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[9].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[10] = new JButton ("-");
		tecladoBotonesCuartaLinea[10].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[10].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[11] = new JButton ("Shift");
		tecladoBotonesCuartaLinea[11].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[11].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesCuartaLinea[12] = new JButton ("^");
		tecladoBotonesCuartaLinea[12].setBackground( Color.DARK_GRAY );
		tecladoBotonesCuartaLinea[12].setForeground( Color.LIGHT_GRAY );
		
		
		//se agregan botones al panel
		for( int l = 0; l < 13; l++ )
			panelTecladoCuartaLinea.add(tecladoBotonesCuartaLinea[l]);
						
		
		
		
		//CODIGO QUINTA LINEA DEL TECLADO////////////////////////////////////////////
		panelTecladoQuintaLinea = new JPanel ();
		panelTecladoQuintaLinea.setLayout( new FlowLayout () );
		panelTecladoQuintaLinea.setBackground( Color.GRAY );
						
		
		tecladoBotonesQuintaLinea = new JButton [4];
		      
		tecladoBotonesQuintaLinea[0] = new JButton ("                                                                     ");
		tecladoBotonesQuintaLinea[0].setBackground( Color.DARK_GRAY );
		tecladoBotonesQuintaLinea[0].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesQuintaLinea[1] = new JButton ("<");
		tecladoBotonesQuintaLinea[1].setBackground( Color.DARK_GRAY );
		tecladoBotonesQuintaLinea[1].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesQuintaLinea[2] = new JButton ("v");
		tecladoBotonesQuintaLinea[2].setBackground( Color.DARK_GRAY );
		tecladoBotonesQuintaLinea[2].setForeground( Color.LIGHT_GRAY );
		tecladoBotonesQuintaLinea[3] = new JButton (">");
		tecladoBotonesQuintaLinea[3].setBackground( Color.DARK_GRAY );
		tecladoBotonesQuintaLinea[3].setForeground( Color.LIGHT_GRAY );
								
		
		JPanel[] espacioIntermedio1 = new JPanel [10];
		espacioIntermedio1[0] = new JPanel ();
		espacioIntermedio1[1] = new JPanel ();
		espacioIntermedio1[2] = new JPanel ();
		espacioIntermedio1[3] = new JPanel ();
		espacioIntermedio1[4] = new JPanel ();
		espacioIntermedio1[5] = new JPanel ();
		espacioIntermedio1[6] = new JPanel ();
		espacioIntermedio1[7] = new JPanel ();
		espacioIntermedio1[8] = new JPanel ();
		espacioIntermedio1[9] = new JPanel ();
		
		
		JPanel[] espacioIntermedio2 = new JPanel [10];
		espacioIntermedio2[0] = new JPanel ();
		espacioIntermedio2[1] = new JPanel ();
		espacioIntermedio2[2] = new JPanel ();
		espacioIntermedio2[3] = new JPanel ();
		espacioIntermedio2[4] = new JPanel ();
		espacioIntermedio2[5] = new JPanel ();
		espacioIntermedio2[6] = new JPanel ();
		espacioIntermedio2[7] = new JPanel ();
		espacioIntermedio2[8] = new JPanel ();
		espacioIntermedio2[9] = new JPanel ();
		
		//AGREGA COLOR A LOS PANELES QUE HACEN ESPACIO ( SOLO PARA DEPURAR )
		
		for ( int x = 0; x < espacioIntermedio1.length ; x++ )
			espacioIntermedio1[x].setBackground(Color.GRAY);
		
		//pone color al  los paneles 
		for ( int x = 0; x < espacioIntermedio2.length ; x++ )
			espacioIntermedio2[x].setBackground(Color.GRAY);
	
		
		//panelTecladoQuintaLinea.add(espacioIntermedio1);
		for ( int a = 0; a < espacioIntermedio1.length; a++ )
			panelTecladoQuintaLinea.add(espacioIntermedio1[a]);
		
		
		panelTecladoQuintaLinea.add(tecladoBotonesQuintaLinea[0]);
		
		for ( int a = 0; a < espacioIntermedio2.length; a++ )
		panelTecladoQuintaLinea.add(espacioIntermedio2[a]);
		
		panelTecladoQuintaLinea.add(tecladoBotonesQuintaLinea[1]);
		panelTecladoQuintaLinea.add(tecladoBotonesQuintaLinea[2]);
		panelTecladoQuintaLinea.add(tecladoBotonesQuintaLinea[3]);
		
		
		
		panelSur = new JPanel ();
		panelSur.setLayout( new GridLayout ( 7 , 1 ) );
		panelSur.setBackground(Color.DARK_GRAY);
		
		
		panelSur.add(botonPangramas);
		panelSur.add(panelPangramas);
		panelSur.add(panelTecladoPrimerLinea);
		panelSur.add(panelTecladoSegundaLinea);
		panelSur.add(panelTecladoTercerLinea);
		panelSur.add(panelTecladoCuartaLinea);
		panelSur.add(panelTecladoQuintaLinea);
		
		add( panelSur, BorderLayout.SOUTH );
		
		
		ManejadorBotones manejadorBotones = new ManejadorBotones ();
		
		ManejadorTeclas manejadorTeclas = new ManejadorTeclas ();
		
		ManejadorMouse manejadorMouse = new ManejadorMouse ();
		
		//se agregan los coponentes de escucha 
		
		for ( int i = 0; i < tecladoBotonesPrimerLinea.length; i++ ){
			tecladoBotonesPrimerLinea[i].addActionListener(manejadorBotones);
		}
		for ( int i = 0; i < tecladoBotonesSegundaLinea.length; i++ ){
			tecladoBotonesSegundaLinea[i].addActionListener(manejadorBotones);
		}
		for ( int i = 0; i < tecladoBotonesTercerLinea.length; i++ ){
			tecladoBotonesTercerLinea[i].addActionListener(manejadorBotones);
		}
		for ( int i = 0; i < tecladoBotonesCuartaLinea.length; i++ ){
			tecladoBotonesCuartaLinea[i].addActionListener(manejadorBotones);
		}
		for ( int i = 0; i < tecladoBotonesQuintaLinea.length; i++ ){
			tecladoBotonesQuintaLinea[i].addActionListener(manejadorBotones);
		}
		
		
		panelNorte.addMouseListener(manejadorMouse);
		panelSur.addMouseListener(manejadorMouse);
		areaTexto.addKeyListener(manejadorTeclas);
		miVentana.addWindowListener(
				new WindowAdapter(){
					
					@Override//From miniized to normal
					public void windowDeiconified( WindowEvent e ){
						
						
					}
					@Override//From normal to minimized
					public void windowIconified ( WindowEvent e ){
						
						
					}
					
					
					
					
				});
		
		areaTexto.requestFocusInWindow();
		
		
				
		
	}//fin del constructor
	
	private class ManejadorBotones implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			areaTexto.requestFocusInWindow();
		}
		
	}
	
	private class ManejadorMouse implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			areaTexto.requestFocusInWindow();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			areaTexto.requestFocusInWindow();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			areaTexto.requestFocusInWindow();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			areaTexto.requestFocusInWindow();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			areaTexto.requestFocusInWindow();
		}
		
	}//fin de la clase privada manejadore de eventos del raton
	
		
	public static JTextArea obtenerAreaTexto(){
		return areaTexto;
	}
	
	
	
	//contabiliza los errores en cada letra
	private void procesarCaracteresErroneos (){
		
		char a, b = 'x';
		boolean bandera = false;
		boolean repite = false;
		
		int indiceArray = 0;
		char[] caracterTemp = new char[50];
		int [] caracteresError = new int [50];
		
		int limite = 0; 
		if ( pasosDelJuego == PANGRAMA_5 ){
			limite = caracteresErrores.length - 10;
		}else{
			limite = caracteresErrores.length;
		}
		
		for ( int r = 0; r < limite; r++){
			for (int c = 0; c  < erroresPangrama[r]; c++){
				
				a = caracteresErrores[r][c];
				
				
				for ( int i = 0; i <= indiceArray;i++ ){
					
					if (a == caracterTemp[i]) {
						caracteresError[i]++;
						//System.out.println(a+"+"+caracteresError[i]);
						repite = true;
					}
					
				}//fin de for
				
								
				if ( bandera ==  false ){
					b = a;
					bandera = true;
					caracterTemp[indiceArray] = a;
					caracteresError[indiceArray]++;
					//System.out.println(b);
					
				}
					
				
				if (a != b && repite != true  ){
					b = a;
					caracterTemp[++indiceArray] = b;
					caracteresError[indiceArray]++;
					//System.out.println(b);
					
				}
				
				repite = false;				
			}//fin de for2	
		}//fin de for1
		
		
		int contadorCaracteresErroneos = 0;
		
		
		while ( caracteresError[contadorCaracteresErroneos] !=  0)
			contadorCaracteresErroneos++;
				
		int  mayor = 0, menor = 0;//pendientw para implementar lista decendente
		
		areaTexto.append("Letras en que te equivocaste y numero de errores que tuviste en cada una:\n");
		for ( int x = 0; x < contadorCaracteresErroneos; x++ ){
			if ( caracterTemp[x] == ' ' )
				areaTexto.append("Tecla spacio");
			
			String errores;
			if ( caracteresError[x] == 1 )
				errores = "error";
			else
				errores = "errores";
			
			String informe = String.format("%c : %d %s", caracterTemp[x], caracteresError[x], errores);
			areaTexto.append(informe+"\n");
		}
											
				
		/*
		 * IMPLEMENTAR UN METODO QUEME DE LA SUMA DE TODOS LOS CARACTERES 
		 * PARA AL FINAL PODER BORRAR LA Z//////////////////////////////OJO//////////
		 */
		
		
	}//fin de metodo procesarCaracteresErroneos
	
	public static void establecerTextoAreaPangramas ( String pangrama ){
		areaPangramas.setText(pangrama);
	}
	
		
	//metodo que se llama para poder borrar la pantalla 
	public static void borrarPantalla ( boolean borrarPantalla  ){
		//borrarPantalla = borrarP;
		if ( borrarPantalla = true )
			areaTexto.setText(null);
	
		areaTexto.requestFocusInWindow();
		
	}//fin de metodo borrarPantalla
	
	//metodo que detecta cuando el usuario escribe en la pantalla
	public static boolean usuarioEscribe (){
		
		/*
		 * Desarrolar algoritmo que haga una comparacion del primer valor
		 * ingresadocon el ultimo valor de ahi saque la diferencia y tome la 
		 * medida entre los dos valores y regres true cada ves que escriba 
		 * 
		 */
		int primerValor ;
		
		if ( contadorCaracteres > 0 ){
			JuegoPangramas.seleccionaTextoMientrasEscribe();
			primerValor = contadorCaracteres;
			return true;
		}else
			return false;
		
			
		
	}//fin del metodo usuarioEsribe
	
	
	
	private class ManejadorTeclas implements KeyListener{
		
		@Override
		public void keyTyped(KeyEvent e) {
			
			
			if ( Pangramas.ejecutaJuego() ){
				
				
				switch ( pasosDelJuego ){
				case PANGRAMA_0:
					
					
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_0] = 0;
						erroresPangrama[PANGRAMA_0] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_0]+" Aciertos = "+aciertosPangrama[PANGRAMA_0]);
						demasiadosErrores = false;
					}
										
					if ( contadorCaracteres  < arregloPangramasString[0].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[0].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
							
							
							aciertosPangrama[PANGRAMA_0]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_0]+" Aciertos = "+aciertosPangrama[PANGRAMA_0]);
														
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[0].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_0] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_0]+" Aciertos = "+aciertosPangrama[PANGRAMA_0]+
										"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_0]+" Aciertos = "+aciertosPangrama[PANGRAMA_0]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
								
							}
							
							
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								
								/*
								//AQUI SE IMPLEMENTA EL SONIDO( NO FUNCIONA )
								try{
									archivoSonido = AudioSystem.getAudioInputStream( new File("error.wav") );
								}catch( IOException e1 ){
									String mensaje = String.format("Excepcion : \n%s", e1);
									JOptionPane.showMessageDialog(miVentana, mensaje);
								}catch( UnsupportedAudioFileException e2 ){
									String mensaje = String.format("Excepcion : \n%s", e2);
									JOptionPane.showMessageDialog(miVentana, mensaje);
								}
								
								try{
									clip = AudioSystem.getClip();
									clip.open(archivoSonido);
									//clip.start();
								}catch( LineUnavailableException e1 ){
									String mensaje = String.format("Excepcion : \n%s", e1);
									JOptionPane.showMessageDialog(miVentana, mensaje);
								}catch( SecurityException e2 ){
									String mensaje = String.format("Excepcion : \n%s", e2);
									JOptionPane.showMessageDialog(miVentana, mensaje);
								}catch(IllegalArgumentException e3 ){
									String mensaje = String.format("Excepcion : \n%s", e3);
									JOptionPane.showMessageDialog(miVentana, mensaje);
								} catch (IOException e4) {
									// TODO Auto-generated catch block
									e4.printStackTrace();
								}
																				
								clip.start();
															
								//clip.drain();
								clip.stop();
													
								clip = null;
								archivoSonido = null;
								*///NO SIRVE
								if ( erroresPangrama[PANGRAMA_0] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_0][erroresPangrama[PANGRAMA_0]] = arregloPangramasString[0].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_0, PANGRAMA_0, erroresPangrama[PANGRAMA_0], caracteresErrores [PANGRAMA_0][ erroresPangrama[PANGRAMA_0] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[0].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
									
									/*
									 * INICIALIZAR ARREGLOS DE CARACTERES OTRA VEZ UNA VEZ QUE EL USUARIO
									 * ESTE ENSU SEGUNDO INTENTO
									 */
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_0]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_0]+" Aciertos = "+aciertosPangrama[PANGRAMA_0]);
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
				
					}//fin de primer if 
					
									
					
					break;
				case PANGRAMA_1:
					
									
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_1] =0;
						erroresPangrama[PANGRAMA_1] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_1]+" Aciertos = "+aciertosPangrama[PANGRAMA_1]);
						demasiadosErrores = false;
					}
					
						
					
					if ( contadorCaracteres  < arregloPangramasString[1].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[1].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
							
							
							aciertosPangrama[PANGRAMA_1]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_1]+" Aciertos = "+aciertosPangrama[PANGRAMA_1]);
														
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[1].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_1] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_1]+" Aciertos = "+aciertosPangrama[PANGRAMA_1]+
										"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
							
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_1]+" Aciertos = "+aciertosPangrama[PANGRAMA_1]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
							
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
																
								if ( erroresPangrama[PANGRAMA_1] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_1][erroresPangrama[PANGRAMA_1]] = arregloPangramasString[1].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_1, PANGRAMA_1, erroresPangrama[PANGRAMA_1], caracteresErrores [PANGRAMA_1][ erroresPangrama[PANGRAMA_1] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[1].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_1]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_1]+" Aciertos = "+aciertosPangrama[PANGRAMA_1]);
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
				
					}//fin de primer if 
						
										
					break;
				case PANGRAMA_2:
					
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_2] = 0;
						erroresPangrama[PANGRAMA_2] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_2]+" Aciertos = "+aciertosPangrama[PANGRAMA_2]);
						demasiadosErrores = false;
					}
						
					
					if ( contadorCaracteres  < arregloPangramasString[2].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[2].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
						
							
							aciertosPangrama[PANGRAMA_2]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_2]+" Aciertos = "+aciertosPangrama[PANGRAMA_2]);
							
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[2].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_2] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_2]+" Aciertos = "+aciertosPangrama[PANGRAMA_2]+
										"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_2]+" Aciertos = "+aciertosPangrama[PANGRAMA_2]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
							
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_2] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_2][erroresPangrama[PANGRAMA_2]] = arregloPangramasString[2].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_2, PANGRAMA_2, erroresPangrama[PANGRAMA_2], caracteresErrores [PANGRAMA_2][ erroresPangrama[PANGRAMA_2] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[2].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_2]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_2]+" Aciertos = "+aciertosPangrama[PANGRAMA_2]);
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
				
					}//fin de primer if 
					
					break;
				case PANGRAMA_3:
					
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_3] = 0;
						erroresPangrama[PANGRAMA_3] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_3]+" Aciertos = "+aciertosPangrama[PANGRAMA_3]);
						demasiadosErrores = false;
					}
						
					
					if ( contadorCaracteres  < arregloPangramasString[3].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[3].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
							
							
							aciertosPangrama[PANGRAMA_3]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_3]+" Aciertos = "+aciertosPangrama[PANGRAMA_3]);
														
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[3].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_3] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_3]+" Aciertos = "+aciertosPangrama[PANGRAMA_3]+
										"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
							
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_3]+" Aciertos = "+aciertosPangrama[PANGRAMA_3]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
							
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_3] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_3][erroresPangrama[PANGRAMA_3]] = arregloPangramasString[3].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_3, PANGRAMA_3, erroresPangrama[PANGRAMA_3], caracteresErrores [PANGRAMA_3][ erroresPangrama[PANGRAMA_3] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[3].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								erroresPangrama[PANGRAMA_3]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_3]+" Aciertos = "+aciertosPangrama[PANGRAMA_3]);
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
				
					}//fin de primer if 
					
					break;
				case PANGRAMA_4:
					
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_4] = 0;
						erroresPangrama[PANGRAMA_4] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_4]+" Aciertos = "+aciertosPangrama[PANGRAMA_4]);
						demasiadosErrores = false;
					}
						
					
					if ( contadorCaracteres  < arregloPangramasString[4].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[4].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
						
							
							aciertosPangrama[PANGRAMA_4]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_4]+" Aciertos = "+aciertosPangrama[PANGRAMA_4]);
														
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[4].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_4] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_4]+" Aciertos = "+aciertosPangrama[PANGRAMA_4]+
										"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
							
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_4]+" Aciertos = "+aciertosPangrama[PANGRAMA_4]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
							
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_4] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_4][erroresPangrama[PANGRAMA_4]] = arregloPangramasString[4].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_4, PANGRAMA_4, erroresPangrama[PANGRAMA_4], caracteresErrores [PANGRAMA_4][ erroresPangrama[PANGRAMA_4] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[4].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_4]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_4]+" Aciertos = "+aciertosPangrama[PANGRAMA_4]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
				
					}//fin de primer if 
					
					break;
				case PANGRAMA_5:
					
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_5] = 0;
						erroresPangrama[PANGRAMA_5] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_5]+" Aciertos = "+aciertosPangrama[PANGRAMA_5]);
						demasiadosErrores = false;
					}
						
					
					if ( contadorCaracteres  < arregloPangramasString[5].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[5].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
						
							
							aciertosPangrama[PANGRAMA_5]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_5]+" Aciertos = "+aciertosPangrama[PANGRAMA_5]);
														
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[5].length() ){
								
								int opcion;
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_5] == 0 )
									opcion = JOptionPane.showConfirmDialog(miVentana, "Excelente, no hubo errores!\n\nTerminaste los primeros pangramas, pero aun hay mas para practicar.\n"
											+ "Deseas seguir jugando?", 
											"Informacion", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
								else	
									opcion = JOptionPane.showConfirmDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_5]+" Aciertos = "+aciertosPangrama[PANGRAMA_5]+
										"\n\nTerminaste los primeros pangramas, pero aun hay mas para practicar.\n"
											+ "Deseas seguir?", "Informacion", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
								
								
								
								if ( opcion == JOptionPane.YES_OPTION ){
									JuegoPangramas.cambiaPangrama();
									
									JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_5]+" Aciertos = "+aciertosPangrama[PANGRAMA_5]);
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
									pasosDelJuego++;//avanza hacia el siguiente pangrama

								}else{
									//AQUI SE DEBE DE IMPLEMENTAR EL INFOREM FINAL DE LOS PRIMEROS  EJERCICIOS
									areaTexto.setText(null);//borra la pantalla
																					
									
									String titulo = String.format("REPORTE FINAL\n");
									areaTexto.append(titulo);
									
									char caracterError;
																				
									
									for( int r = 0; r < 6; r++){
										String ejercicio = String.format("Ejercicio %d : aciertos = %d, errores = %d\n", r+1, aciertosPangrama[r], erroresPangrama[r] );
										areaTexto.append(ejercicio);
										areaTexto.append("Caracteres en que sucedieron los errores : ");
										
										if ( erroresPangrama[r] == 0 )
											areaTexto.append("Ninguno");
										else{
											for (int c = 0; c < erroresPangrama[r]; c++){
												caracterError = caracteresErrores[r][c];
												String caracter = String.format("%c, ", caracterError);
												areaTexto.append(caracter);
											}
										}
										areaTexto.append("\n\n");
											
									}//fin de primer for
									
									
									//contabiliza los errores en cada letra
									procesarCaracteresErroneos();
									
									int totalErrores = 0;
									for ( int i = 0; i <= 5; i++ )
										totalErrores += erroresPangrama[i];
									
									
									areaTexto.append("\nTotal de errores : "+totalErrores+"\n");
									
																		
									//restablecerValores
									contadorCaracteres = 0;
									pasosDelJuego = 0;
									JuegoPangramas.terminoJuego();	
									for ( int v = 0; v <= 5; v++ ){
										erroresPangrama[v] = 0;
										aciertosPangrama[v] = 0;
									}
									
								}//fin de else muestra reporte
															
							}//fin de if se completa el pangrama
												
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_5] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_5][erroresPangrama[PANGRAMA_5]] = arregloPangramasString[5].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_5, PANGRAMA_5, erroresPangrama[PANGRAMA_5], caracteresErrores [PANGRAMA_5][ erroresPangrama[PANGRAMA_5] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[5].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_5]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_5]+" Aciertos = "+aciertosPangrama[PANGRAMA_5]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
				
					}//fin de primer if 
					
					break;
				case PANGRAMA_6:
	
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
	
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_6] = 0;
						erroresPangrama[PANGRAMA_6] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_6]+" Aciertos = "+aciertosPangrama[PANGRAMA_6]);
						demasiadosErrores = false;
					}
					
		
	
					if ( contadorCaracteres  < arregloPangramasString[6].length() ){
		
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[6].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
							
			
							aciertosPangrama[PANGRAMA_6]++;
			
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_6]+" Aciertos = "+aciertosPangrama[PANGRAMA_6]);
										
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
			
							if ( contadorCaracteres == arregloPangramasString[6].length() ){
				
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_6] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_6]+" Aciertos = "+aciertosPangrama[PANGRAMA_6]+
											"\nTrata de hacerlo mejor en el siguiente!!");
				
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_6]+" Aciertos = "+aciertosPangrama[PANGRAMA_6]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
			
			
						}else{//PROCESA LOS ERRORES
			
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
				
								if ( erroresPangrama[PANGRAMA_6] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_6][erroresPangrama[PANGRAMA_6]] = arregloPangramasString[6].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_6, PANGRAMA_6, erroresPangrama[PANGRAMA_6], caracteresErrores [PANGRAMA_6][ erroresPangrama[PANGRAMA_6] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[6].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_6]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_6]+" Aciertos = "+aciertosPangrama[PANGRAMA_6]);
								
				
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
			
			
						}//fin de else ERRORES 

					}//fin de primer if 
	
					break;
				case PANGRAMA_7:
	
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_7] = 0;
						erroresPangrama[PANGRAMA_7] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_7]+" Aciertos = "+aciertosPangrama[PANGRAMA_7]);
						demasiadosErrores = false;
					}
	
		
	
					if ( contadorCaracteres  < arregloPangramasString[7].length() ){
		
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[7].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
							
			
							aciertosPangrama[PANGRAMA_7]++;
			
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_7]+" Aciertos = "+aciertosPangrama[PANGRAMA_7]);
										
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
			
							if ( contadorCaracteres == arregloPangramasString[7].length() ){
				
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_7] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_7]+" Aciertos = "+aciertosPangrama[PANGRAMA_7]+
											"\nTrata de hacerlo mejor en el siguiente!!");
				
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_7]+" Aciertos = "+aciertosPangrama[PANGRAMA_7]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
			
			
						}else{//PROCESA LOS ERRORES
			
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
				
								if ( erroresPangrama[PANGRAMA_7] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_7][erroresPangrama[PANGRAMA_7]] = arregloPangramasString[7].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_7, PANGRAMA_7, erroresPangrama[PANGRAMA_7], caracteresErrores [PANGRAMA_7][ erroresPangrama[PANGRAMA_7] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[7].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_7]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_7]+" Aciertos = "+aciertosPangrama[PANGRAMA_7]);
								
				
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
			
			
						}//fin de else ERRORES 

					}//fin de primer if 
	
					break;
				case PANGRAMA_8:
	
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
	
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_8] = 0;
						erroresPangrama[PANGRAMA_8] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_8]+" Aciertos = "+aciertosPangrama[PANGRAMA_8]);
						demasiadosErrores = false;
					}
		
	
					if ( contadorCaracteres  < arregloPangramasString[8].length() ){
		
						//	PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[8].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
						
			
							aciertosPangrama[PANGRAMA_8]++;
			
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_8]+" Aciertos = "+aciertosPangrama[PANGRAMA_8]);
										
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
			
							if ( contadorCaracteres == arregloPangramasString[8].length() ){
				
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_8] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_8]+" Aciertos = "+aciertosPangrama[PANGRAMA_8]+
											"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_8]+" Aciertos = "+aciertosPangrama[PANGRAMA_8]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
			
			
						}else{//PROCESA LOS ERRORES
			
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_8] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_8][erroresPangrama[PANGRAMA_8]] = arregloPangramasString[8].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_8, PANGRAMA_8, erroresPangrama[PANGRAMA_8], caracteresErrores [PANGRAMA_8][ erroresPangrama[PANGRAMA_8] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[8].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_8]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_8]+" Aciertos = "+aciertosPangrama[PANGRAMA_8]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 

					}//fin de primer if 
					
					break;
				case PANGRAMA_9:
					
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_9] = 0;
						erroresPangrama[PANGRAMA_9] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_9]+" Aciertos = "+aciertosPangrama[PANGRAMA_9]);
						demasiadosErrores = false;
					}
					
					
					if ( contadorCaracteres  < arregloPangramasString[9].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[9].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
					
							
							aciertosPangrama[PANGRAMA_9]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_9]+" Aciertos = "+aciertosPangrama[PANGRAMA_9]);
							
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[9].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_9] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_9]+" Aciertos = "+aciertosPangrama[PANGRAMA_9]+
											"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_9]+" Aciertos = "+aciertosPangrama[PANGRAMA_9]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
							
						}else{//PROCESA LOS ERRORES
			
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_9] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_9][erroresPangrama[PANGRAMA_9]] = arregloPangramasString[9].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_9, PANGRAMA_9, erroresPangrama[PANGRAMA_9], caracteresErrores [PANGRAMA_9][ erroresPangrama[PANGRAMA_9] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[9].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_9]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_9]+" Aciertos = "+aciertosPangrama[PANGRAMA_9]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
			
							
						}//fin de else ERRORES 

					}//fin de primer if 
	
					break;
				case PANGRAMA_10:
					
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_10] = 0;
						erroresPangrama[PANGRAMA_10] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_10]+" Aciertos = "+aciertosPangrama[PANGRAMA_10]);
						demasiadosErrores = false;
					}
	
					if ( contadorCaracteres  < arregloPangramasString[10].length() ){
		
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[10].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
					
							
							aciertosPangrama[PANGRAMA_10]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_10]+" Aciertos = "+aciertosPangrama[PANGRAMA_10]);
											
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[10].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_10] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_10]+" Aciertos = "+aciertosPangrama[PANGRAMA_10]+
											"\nTrata de hacerlo mejor en el siguiente!!");
				
								JuegoPangramas.cambiaPangrama();
							
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_10]+" Aciertos = "+aciertosPangrama[PANGRAMA_10]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
			
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_10] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_10][erroresPangrama[PANGRAMA_10]] = arregloPangramasString[10].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_10, PANGRAMA_10, erroresPangrama[PANGRAMA_10], caracteresErrores [PANGRAMA_10][ erroresPangrama[PANGRAMA_10] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[10].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_10]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_10]+" Aciertos = "+aciertosPangrama[PANGRAMA_10]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
						
					}//fin de primer if 
	
					break;
				case PANGRAMA_11:
	
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
	
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_11] = 0;
						erroresPangrama[PANGRAMA_11] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_11]+" Aciertos = "+aciertosPangrama[PANGRAMA_11]);
						demasiadosErrores = false;
					}
		
	
					if ( contadorCaracteres  < arregloPangramasString[11].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[11].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
							
							
							aciertosPangrama[PANGRAMA_11]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_11]+" Aciertos = "+aciertosPangrama[PANGRAMA_11]);
							
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[11].length() ){
				
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_11] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_11]+" Aciertos = "+aciertosPangrama[PANGRAMA_11]+
											"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_11]+" Aciertos = "+aciertosPangrama[PANGRAMA_11]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
							
						}else{//PROCESA LOS ERRORES
			
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
				
								if ( erroresPangrama[PANGRAMA_11] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_11][erroresPangrama[PANGRAMA_11]] = arregloPangramasString[11].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_11, PANGRAMA_11, erroresPangrama[PANGRAMA_11], caracteresErrores [PANGRAMA_11][ erroresPangrama[PANGRAMA_11] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[11].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_11]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_11]+" Aciertos = "+aciertosPangrama[PANGRAMA_11]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
						
					}//fin de primer if 
	
					break;
				case PANGRAMA_12:
	
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
	
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_12] = 0;
						erroresPangrama[PANGRAMA_12] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_12]+" Aciertos = "+aciertosPangrama[PANGRAMA_12]);
						demasiadosErrores = false;
					}
		
	
					if ( contadorCaracteres  < arregloPangramasString[12].length() ){
		
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[12].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
						
							
							aciertosPangrama[PANGRAMA_12]++;
			
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_12]+" Aciertos = "+aciertosPangrama[PANGRAMA_12]);
										
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
			
							if ( contadorCaracteres == arregloPangramasString[12].length() ){
				
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_12] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_12]+" Aciertos = "+aciertosPangrama[PANGRAMA_12]+
											"\nTrata de hacerlo mejor en el siguiente!!");
				
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_12]+" Aciertos = "+aciertosPangrama[PANGRAMA_12]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
			
			
						}else{//PROCESA LOS ERRORES
			
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_12] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_12][erroresPangrama[PANGRAMA_12]] = arregloPangramasString[12].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_12, PANGRAMA_12, erroresPangrama[PANGRAMA_12], caracteresErrores [PANGRAMA_12][ erroresPangrama[PANGRAMA_12] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[12].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_12]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_12]+" Aciertos = "+aciertosPangrama[PANGRAMA_12]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
						
					}//fin de primer if 
					
					break;
				case PANGRAMA_13:
					
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//	areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_13] = 0;
						erroresPangrama[PANGRAMA_13] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_13]+" Aciertos = "+aciertosPangrama[PANGRAMA_13]);
						demasiadosErrores = false;
					}
					
					
					if ( contadorCaracteres  < arregloPangramasString[13].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[13].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
						
							
							aciertosPangrama[PANGRAMA_13]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_13]+" Aciertos = "+aciertosPangrama[PANGRAMA_13]);
							
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[13].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_13] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_13]+" Aciertos = "+aciertosPangrama[PANGRAMA_13]+
											"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_13]+" Aciertos = "+aciertosPangrama[PANGRAMA_13]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
							
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								
								if ( erroresPangrama[PANGRAMA_13] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_13][erroresPangrama[PANGRAMA_13]] = arregloPangramasString[13].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_13, PANGRAMA_13, erroresPangrama[PANGRAMA_13], caracteresErrores [PANGRAMA_13][ erroresPangrama[PANGRAMA_13] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[13].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_13]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_13]+" Aciertos = "+aciertosPangrama[PANGRAMA_13]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
						
					}//fin de primer if 
	
					break;
				case PANGRAMA_14:
					
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
	
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_14] = 0;
						erroresPangrama[PANGRAMA_14] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_14]+" Aciertos = "+aciertosPangrama[PANGRAMA_14]);
						demasiadosErrores = false;
					}
					
					
					if ( contadorCaracteres  < arregloPangramasString[14].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[14].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
							
							
							aciertosPangrama[PANGRAMA_14]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_14]+" Aciertos = "+aciertosPangrama[PANGRAMA_14]);
							
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[14].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_14] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!");
								else	
									JOptionPane.showMessageDialog(miVentana, "Errores = "+erroresPangrama[PANGRAMA_14]+" Aciertos = "+aciertosPangrama[PANGRAMA_14]+
											"\nTrata de hacerlo mejor en el siguiente!!");
								
								JuegoPangramas.cambiaPangrama();
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_14]+" Aciertos = "+aciertosPangrama[PANGRAMA_14]);
								contadorCaracteres = 0;//establece  contadorCaracteres a 0
								pasosDelJuego++;//avanza hacia el siguiente pangrama
							}
							
							
						}else{//PROCESA LOS ERRORES
							
							//evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
								
								if ( erroresPangrama[PANGRAMA_14] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_14][erroresPangrama[PANGRAMA_14]] = arregloPangramasString[14].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_14, PANGRAMA_14, erroresPangrama[PANGRAMA_14], caracteresErrores [PANGRAMA_14][ erroresPangrama[PANGRAMA_14] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[14].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								
								erroresPangrama[PANGRAMA_14]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_14]+" Aciertos = "+aciertosPangrama[PANGRAMA_14]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
							
							
						}//fin de else ERRORES 
						
					}//fin de primer if 
					
					break;
				case PANGRAMA_15:
	
					if ( terminoPangrama == true ){
						areaTexto.setText(null);
						//areaTexto.setSelectionStart(0);
						//areaTexto.setSelectionEnd(arregloPangramasString[0].length()+1);
						terminoPangrama = false;
					}
					
					if ( demasiadosErrores == true ){
						areaTexto.setText(null);
						aciertosPangrama[PANGRAMA_15] = 0;
						erroresPangrama[PANGRAMA_15] = 0;
						JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_15]+" Aciertos = "+aciertosPangrama[PANGRAMA_15]);
						demasiadosErrores = false;
					}
					
					if ( contadorCaracteres  < arregloPangramasString[15].length() ){
						
						//PROCESA LOS ACIERTOS						
						if ( e.getKeyChar() == arregloPangramasString[15].charAt(contadorCaracteres) ){
							JuegoPangramas.seleccionaTextoMientrasEscribe();
							//esta variable se debe de incrementar al final 
							contadorCaracteres++;
							areaPangramas.setSelectionStart(0);
							areaPangramas.setSelectionEnd(contadorCaracteres);
						
							
							aciertosPangrama[PANGRAMA_15]++;
							
							JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_15]+" Aciertos = "+aciertosPangrama[PANGRAMA_15]);
							
							etiquetaAciertos.setText("!Bien!");
							etiquetaAciertos.setForeground( Color.BLUE );
							etiquetaAciertos2.setText("!Bien!");
							etiquetaAciertos2.setForeground( Color.BLUE );
							panelColorAcierto.setBackground(Color.BLUE);
							panelColorAcierto2.setBackground(Color.BLUE);
							
							if ( contadorCaracteres == arregloPangramasString[15].length() ){
								
								terminoPangrama = true;
								if ( erroresPangrama[PANGRAMA_15] == 0 )
									JOptionPane.showMessageDialog(miVentana, "Excelente, no hubo errores!\nTerminaste!!\nCompletaste todos los ejercicios");
								else	
									JOptionPane.showMessageDialog(miVentana, "Terminaste!!\nCompletaste todos los ejercicios");
								
								/*
								 * CUANDO TERMINE EL USUARIO DE HACER EL ULTIMO EJERCICIO 
								 * SE TIENE QUE RESTABLECER LOS VALORES , PARA QUE CUANDO 
								 * VUELTA OPRIMIR EL BOTON DE PANGRAMAS EL USUARIO PUEDA JUGAR 
								 * DENUEVO DESDE EL PRINCIPIO
								 */
								
								//INFORME FINAL DE LOS PRIMEROS  EJERCICIOS
								areaTexto.setText(null);//borra la pantalla
								
								String titulo = String.format("REPORTE FINAL\n");
								areaTexto.append(titulo);
								
								char caracterError;
								
								for( int r = 0; r < arregloPangramasString.length; r++){
									String ejercicio = String.format("Ejercicio %d : aciertos = %d, errores = %d\n", r+1, aciertosPangrama[r], erroresPangrama[r] );
									areaTexto.append(ejercicio);
									areaTexto.append("Caracteres en que sucedieron los errores : ");
									
									if ( erroresPangrama[r] == 0 )
										areaTexto.append("Ninguno");
									else{
										for (int c = 0; c < erroresPangrama[r]; c++){
											caracterError = caracteresErrores[r][c];
											String caracter = String.format("%c, ", caracterError);
											areaTexto.append(caracter);
										}
									}
									
									areaTexto.append("\n\n");
										
								}//fin de primer for
								
								//contabiliza los errores en cada letra
								procesarCaracteresErroneos();
								
								int totalErrores = 0;
								for ( int i = 0; i <= 15; i++ )
									totalErrores += erroresPangrama[i];
								
								
								areaTexto.append("\nTotal de errores : "+totalErrores+"\n");
								
								//restablecerValores
								contadorCaracteres = 0;
								pasosDelJuego = 0;
								JuegoPangramas.terminoJuego();
								for ( int v = 0; v <= 15; v++ ){
									erroresPangrama[v] = 0;
									aciertosPangrama[v] = 0;
								}
								
							}//fin de if completo el pangraman
							
							
						}else{//PROCESA LOS ERRORES
							
							//	evita que la tecla de borrar genere errores ( no sirve )
							if ( e.getKeyCode() !=  KeyEvent.VK_BACK_SPACE ){
				
								if ( erroresPangrama[PANGRAMA_15] < 100 ){
									
									try{
										caracteresErrores[PANGRAMA_15][erroresPangrama[PANGRAMA_15]] = arregloPangramasString[15].charAt(contadorCaracteres);
										//System.out.printf("PANGRAMA %d, caracteresErrores[%d][%d] = %c, ( contadorCaracteres = %d ), ", PANGRAMA_15, PANGRAMA_15, erroresPangrama[PANGRAMA_15], caracteresErrores [PANGRAMA_15][ erroresPangrama[PANGRAMA_15] ], contadorCaracteres);
										//System.out.printf("Cantidad caracteres pangrama = %d\n", arregloPangramasString[15].length() );
									}catch( Exception excepcion ){
										String mensaje = String.format("Excepcion : \n%s", excepcion);
										JOptionPane.showMessageDialog(miVentana, mensaje);
										excepcion.printStackTrace();
									}
								
								}else{
									demasiadosErrores = true;									
									JOptionPane.showMessageDialog(miVentana, "Sobrepasaste el limite de los errores permitidos!"
											+"\nComenzaras de nuevo desde el principio."
											+ "\n\nNota : No necesitas borrar lo que escribiste en la pantalla "
											+ "\nse borrara en cuanto escribas la primera letra del pangrama");
									
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionStart(0);
									//JuegoPangramas.obtenerAreaTextoPangramas().setSelectionEnd(0);
									JuegoPangramas.establecerContadorCaracteres(0);
									
								
									contadorCaracteres = 0;//establece  contadorCaracteres a 0
								}
								
								
								etiquetaAciertos.setText("!Mal!");
								etiquetaAciertos.setForeground( Color.RED );
								etiquetaAciertos2.setText("!Mal!");
								etiquetaAciertos2.setForeground( Color.RED );
								panelColorAcierto.setBackground(Color.RED);
								panelColorAcierto2.setBackground(Color.RED);
								
								erroresPangrama[PANGRAMA_15]++;
								
								JuegoPangramas.obtenerBarraEstado().setText("Errores = "+erroresPangrama[PANGRAMA_15]+" Aciertos = "+aciertosPangrama[PANGRAMA_15]);
								
								
							}//fin de if evita tecla borras ( KeyEvent.VK_BACK_SPACE ) 
			
							
						}//fin de else ERRORES 

					}//fin de primer if 
					break;
				
					
				}//fin de switch
				
				
							
				
			}//fin de if ( Pangramas.ejecutaJuego )
				
					
		}//fin del metodo kayTyoed

		@Override				
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			//String tecla = e.getKeyText(e.getKeyCode());
			int codigoTecla = e.getKeyCode();
			//areaTexto.append(codigoTecla+", ");//PARA DEPURAR
			
			
			//JuegoPangramas.seleccionaTextoMientrasEscribe();PENDITENTE
					
			switch(codigoTecla){
			case KeyEvent.VK_CAPS_LOCK:
				tecladoBotonesTercerLinea[0].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesTercerLinea[0].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_PERIOD:
				tecladoBotonesCuartaLinea[9].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesCuartaLinea[9].setForeground(Color.BLACK);
				break;
			case LETRA_ENIE://no
				tecladoBotonesTercerLinea[10].setBackground( Color.LIGHT_GRAY );
				tecladoBotonesTercerLinea[10].setForeground( Color.BLACK );
				break;
			case APOSTROFE:
				tecladoBotonesPrimerLinea[11].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesPrimerLinea[11].setForeground(Color.BLACK);
				break;
			case CORCHETE_ABIERTO:
				tecladoBotonesTercerLinea[11].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesTercerLinea[11].setForeground(Color.BLACK);
				break;
			case CORCHETE_CERRADO:
				tecladoBotonesTercerLinea[12].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesTercerLinea[12].setForeground(Color.BLACK);
				break;
			case KeyEvent.VK_COMMA:
				tecladoBotonesCuartaLinea[8].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesCuartaLinea[8].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_UP:
				tecladoBotonesCuartaLinea[12].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesCuartaLinea[12].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_DOWN:
				tecladoBotonesQuintaLinea[2].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesQuintaLinea[2].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_LEFT:
				tecladoBotonesQuintaLinea[1].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesQuintaLinea[1].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_RIGHT:
				tecladoBotonesQuintaLinea[3].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesQuintaLinea[3].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_SPACE:
				tecladoBotonesQuintaLinea[0].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesQuintaLinea[0].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_SHIFT:
				tecladoBotonesCuartaLinea[0].setBackground(Color.LIGHT_GRAY);
				tecladoBotonesCuartaLinea[0].setForeground(Color.BLACK);
			 	tecladoBotonesCuartaLinea[11].setBackground(Color.LIGHT_GRAY);
			 	tecladoBotonesCuartaLinea[11].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_MINUS:
			 	 tecladoBotonesCuartaLinea[10].setBackground(Color.LIGHT_GRAY);
			 	 tecladoBotonesCuartaLinea[10].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_PLUS:
			 	 tecladoBotonesSegundaLinea[12].setBackground(Color.LIGHT_GRAY);
			 	tecladoBotonesSegundaLinea[12].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_BACK_SPACE:
				
			 	 tecladoBotonesPrimerLinea[13].setBackground(Color.LIGHT_GRAY);
			 	tecladoBotonesPrimerLinea[13].setForeground(Color.BLACK);
			 	break;
			case KeyEvent.VK_TAB://NO SIRVE
			 	 tecladoBotonesSegundaLinea[0].setBackground(Color.LIGHT_GRAY);
			 	tecladoBotonesSegundaLinea[0].setForeground(Color.BLACK);
			 	break;
			 case KeyEvent.VK_ENTER:
			 	 tecladoBotonesTercerLinea[13].setBackground(Color.LIGHT_GRAY);
			 	 tecladoBotonesTercerLinea[13].setForeground(Color.BLACK);
			 	break;
			 case KeyEvent.VK_EXCLAMATION_MARK://mo
				 tecladoBotonesPrimerLinea[12].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[12].setForeground(Color.BLACK);
				break;
			 case KeyEvent.VK_1:
				 tecladoBotonesPrimerLinea[1].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[1].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_2:
				 tecladoBotonesPrimerLinea[2].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[2].setForeground(Color.BLACK);
				  break;
			 case KeyEvent.VK_3:
				 tecladoBotonesPrimerLinea[3].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[3].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_4:
				 tecladoBotonesPrimerLinea[4].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[4].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_5:
				 tecladoBotonesPrimerLinea[5].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[5].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_6:
				 tecladoBotonesPrimerLinea[6].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[6].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_7:
				 tecladoBotonesPrimerLinea[7].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[7].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_8:
				 tecladoBotonesPrimerLinea[8].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[8].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_9:
				 tecladoBotonesPrimerLinea[9].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[9].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_0:
				 tecladoBotonesPrimerLinea[10].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesPrimerLinea[10].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_Q:
				 tecladoBotonesSegundaLinea[1].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[1].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_W:
				 tecladoBotonesSegundaLinea[2].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[2].setForeground(Color.BLACK);
				  break;
			 case KeyEvent.VK_E:
				 tecladoBotonesSegundaLinea[3].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[3].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_R:
				 tecladoBotonesSegundaLinea[4].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[4].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_T:
				 tecladoBotonesSegundaLinea[5].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[5].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_Y:
				 tecladoBotonesSegundaLinea[6].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[6].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_U:
				 tecladoBotonesSegundaLinea[7].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[7].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_I:
				 tecladoBotonesSegundaLinea[8].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[8].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_O:
				 tecladoBotonesSegundaLinea[9].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[9].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_P:
				 tecladoBotonesSegundaLinea[10].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesSegundaLinea[10].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_A:
				 tecladoBotonesTercerLinea[1].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[1].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_S:
				 tecladoBotonesTercerLinea[2].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[2].setForeground(Color.BLACK);
				  break;
			 case KeyEvent.VK_D:
				 tecladoBotonesTercerLinea[3].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[3].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_F:
				 tecladoBotonesTercerLinea[4].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[4].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_G:
				 tecladoBotonesTercerLinea[5].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[5].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_H:
				 tecladoBotonesTercerLinea[6].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[6].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_J:
				 tecladoBotonesTercerLinea[7].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[7].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_K:
				 tecladoBotonesTercerLinea[8].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[8].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_L:
				 tecladoBotonesTercerLinea[9].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesTercerLinea[9].setForeground(Color.BLACK);
				 break;
				 /*PENDIENTE Ñ
			 case KeyEvent.VK_Ñ:
				 tecladoBotonesSegundaLinea[10].setBackground(Color.LIGHT_GRAY);
				 break;
				 */
			 case KeyEvent.VK_Z:
				 tecladoBotonesCuartaLinea[1].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesCuartaLinea[1].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_X:
				 tecladoBotonesCuartaLinea[2].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesCuartaLinea[2].setForeground(Color.BLACK);
				  break;
			 case KeyEvent.VK_C:
				 tecladoBotonesCuartaLinea[3].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesCuartaLinea[3].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_V:
				 tecladoBotonesCuartaLinea[4].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesCuartaLinea[4].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_B:
				 tecladoBotonesCuartaLinea[5].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesCuartaLinea[5].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_N:
				 tecladoBotonesCuartaLinea[6].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesCuartaLinea[6].setForeground(Color.BLACK);
				 break;
			 case KeyEvent.VK_M:
				 tecladoBotonesCuartaLinea[7].setBackground(Color.LIGHT_GRAY);
				 tecladoBotonesCuartaLinea[7].setForeground(Color.BLACK);
				 break;
			 
				 
			}//fin de switch
			 
			 		
		}//fin del metodo kayPressed

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int codigoTecla = e.getKeyCode();
			//areaTexto.append("-");
			
			switch(codigoTecla){
			case KeyEvent.VK_CAPS_LOCK:
				tecladoBotonesTercerLinea[0].setBackground(Color.DARK_GRAY);
				tecladoBotonesTercerLinea[0].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_PERIOD:
				tecladoBotonesCuartaLinea[9].setBackground(Color.DARK_GRAY);
				tecladoBotonesCuartaLinea[9].setForeground(Color.LIGHT_GRAY);
				break;
			case LETRA_ENIE://no
				tecladoBotonesTercerLinea[10].setBackground( Color.DARK_GRAY );
				tecladoBotonesTercerLinea[10].setForeground( Color.LIGHT_GRAY );
				break;
			case APOSTROFE:
				tecladoBotonesPrimerLinea[11].setBackground(Color.DARK_GRAY);
				tecladoBotonesPrimerLinea[11].setForeground(Color.LIGHT_GRAY);
				break;
			case CORCHETE_ABIERTO:
				tecladoBotonesTercerLinea[11].setBackground(Color.DARK_GRAY);
				tecladoBotonesTercerLinea[11].setForeground(Color.LIGHT_GRAY);
				break;
			case CORCHETE_CERRADO:
				tecladoBotonesTercerLinea[12].setBackground(Color.DARK_GRAY);
				tecladoBotonesTercerLinea[12].setForeground(Color.LIGHT_GRAY);
				break;
			case KeyEvent.VK_COMMA:
				tecladoBotonesCuartaLinea[8].setBackground(Color.DARK_GRAY);
				tecladoBotonesCuartaLinea[8].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_UP:
				tecladoBotonesCuartaLinea[12].setBackground(Color.DARK_GRAY);
				tecladoBotonesCuartaLinea[12].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_DOWN:
				tecladoBotonesQuintaLinea[2].setBackground(Color.DARK_GRAY);
				tecladoBotonesQuintaLinea[2].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_LEFT:
				tecladoBotonesQuintaLinea[1].setBackground(Color.DARK_GRAY);
				tecladoBotonesQuintaLinea[1].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_RIGHT:
				tecladoBotonesQuintaLinea[3].setBackground(Color.DARK_GRAY);
				tecladoBotonesQuintaLinea[3].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_SPACE:
				tecladoBotonesQuintaLinea[0].setBackground(Color.DARK_GRAY);
				tecladoBotonesQuintaLinea[0].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_SHIFT:
				tecladoBotonesCuartaLinea[0].setBackground(Color.DARK_GRAY);
				tecladoBotonesCuartaLinea[0].setForeground(Color.LIGHT_GRAY);
			 	tecladoBotonesCuartaLinea[11].setBackground(Color.DARK_GRAY);
			 	tecladoBotonesCuartaLinea[11].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_MINUS:
			 	 tecladoBotonesCuartaLinea[10].setBackground(Color.DARK_GRAY);
			 	 tecladoBotonesCuartaLinea[10].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_PLUS:
			 	 tecladoBotonesSegundaLinea[12].setBackground(Color.DARK_GRAY);
			 	tecladoBotonesSegundaLinea[12].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_BACK_SPACE:
				
			 	 tecladoBotonesPrimerLinea[13].setBackground(Color.DARK_GRAY);
			 	tecladoBotonesPrimerLinea[13].setForeground(Color.LIGHT_GRAY);
			 	break;
			case KeyEvent.VK_TAB://NO SIRVE
			 	 tecladoBotonesSegundaLinea[0].setBackground(Color.DARK_GRAY);
			 	tecladoBotonesSegundaLinea[0].setForeground(Color.LIGHT_GRAY);
			 	break;
			 case KeyEvent.VK_ENTER:
			 	 tecladoBotonesTercerLinea[13].setBackground(Color.DARK_GRAY);
			 	 tecladoBotonesTercerLinea[13].setForeground(Color.LIGHT_GRAY);
			 	break;
			 case KeyEvent.VK_EXCLAMATION_MARK://mo
				 tecladoBotonesPrimerLinea[12].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[12].setForeground(Color.LIGHT_GRAY);
				break;
			 case KeyEvent.VK_1:
				 tecladoBotonesPrimerLinea[1].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[1].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_2:
				 tecladoBotonesPrimerLinea[2].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[2].setForeground(Color.LIGHT_GRAY);
				  break;
			 case KeyEvent.VK_3:
				 tecladoBotonesPrimerLinea[3].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[3].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_4:
				 tecladoBotonesPrimerLinea[4].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[4].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_5:
				 tecladoBotonesPrimerLinea[5].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[5].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_6:
				 tecladoBotonesPrimerLinea[6].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[6].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_7:
				 tecladoBotonesPrimerLinea[7].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[7].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_8:
				 tecladoBotonesPrimerLinea[8].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[8].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_9:
				 tecladoBotonesPrimerLinea[9].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[9].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_0:
				 tecladoBotonesPrimerLinea[10].setBackground(Color.DARK_GRAY);
				 tecladoBotonesPrimerLinea[10].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_Q:
				 tecladoBotonesSegundaLinea[1].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[1].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_W:
				 tecladoBotonesSegundaLinea[2].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[2].setForeground(Color.LIGHT_GRAY);
				  break;
			 case KeyEvent.VK_E:
				 tecladoBotonesSegundaLinea[3].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[3].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_R:
				 tecladoBotonesSegundaLinea[4].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[4].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_T:
				 tecladoBotonesSegundaLinea[5].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[5].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_Y:
				 tecladoBotonesSegundaLinea[6].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[6].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_U:
				 tecladoBotonesSegundaLinea[7].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[7].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_I:
				 tecladoBotonesSegundaLinea[8].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[8].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_O:
				 tecladoBotonesSegundaLinea[9].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[9].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_P:
				 tecladoBotonesSegundaLinea[10].setBackground(Color.DARK_GRAY);
				 tecladoBotonesSegundaLinea[10].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_A:
				 tecladoBotonesTercerLinea[1].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[1].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_S:
				 tecladoBotonesTercerLinea[2].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[2].setForeground(Color.LIGHT_GRAY);
				  break;
			 case KeyEvent.VK_D:
				 tecladoBotonesTercerLinea[3].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[3].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_F:
				 tecladoBotonesTercerLinea[4].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[4].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_G:
				 tecladoBotonesTercerLinea[5].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[5].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_H:
				 tecladoBotonesTercerLinea[6].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[6].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_J:
				 tecladoBotonesTercerLinea[7].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[7].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_K:
				 tecladoBotonesTercerLinea[8].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[8].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_L:
				 tecladoBotonesTercerLinea[9].setBackground(Color.DARK_GRAY);
				 tecladoBotonesTercerLinea[9].setForeground(Color.LIGHT_GRAY);
				 break;
				 /*PENDIENTE Ñ
			 case KeyEvent.VK_Ñ:
				 tecladoBotonesSegundaLinea[10].setBackground(Color.DARK_GRAY);
				 break;
				 */
			 case KeyEvent.VK_Z:
				 tecladoBotonesCuartaLinea[1].setBackground(Color.DARK_GRAY);
				 tecladoBotonesCuartaLinea[1].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_X:
				 tecladoBotonesCuartaLinea[2].setBackground(Color.DARK_GRAY);
				 tecladoBotonesCuartaLinea[2].setForeground(Color.LIGHT_GRAY);
				  break;
			 case KeyEvent.VK_C:
				 tecladoBotonesCuartaLinea[3].setBackground(Color.DARK_GRAY);
				 tecladoBotonesCuartaLinea[3].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_V:
				 tecladoBotonesCuartaLinea[4].setBackground(Color.DARK_GRAY);
				 tecladoBotonesCuartaLinea[4].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_B:
				 tecladoBotonesCuartaLinea[5].setBackground(Color.DARK_GRAY);
				 tecladoBotonesCuartaLinea[5].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_N:
				 tecladoBotonesCuartaLinea[6].setBackground(Color.DARK_GRAY);
				 tecladoBotonesCuartaLinea[6].setForeground(Color.LIGHT_GRAY);
				 break;
			 case KeyEvent.VK_M:
				 tecladoBotonesCuartaLinea[7].setBackground(Color.DARK_GRAY);
				 tecladoBotonesCuartaLinea[7].setForeground(Color.LIGHT_GRAY);
				 break;
						 
			}//fin de switch
					
		}//fin de metodo keyReleased
	
	}//fin de la clase privada manejadora de eventos de teclas
	
}//fin de la clase 
