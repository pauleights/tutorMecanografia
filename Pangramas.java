import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Pangramas extends JFrame{
	
	private final Pangramas ventanaPangramas = this; 
	
	private JLabel pangramaDefinicion, tituloListaPangramas;
	
	private JLabel[] pangramas;//arreglo de pangramas
	
	private JPanel panelNorte, panelCentral;
	
	private JButton botonJugarJuego;
	
	protected static String[] arregloPangramasString;
	
	private static boolean ejecutaJuego;
	
	public Pangramas (){
		
		super("Pangramas");
		
		setBackground( Color.GRAY );
		
		/*
		panelNorte = new JPanel ();
		panelNorte.setLayout( new FlowLayout() );
		
		pangramaDefinicion = new JLabel ( "Pangramas: frase que contiene todas las letras del alfabeto"
				+ " por lo menos una vez" ); 
		
		panelNorte.add(pangramaDefinicion);
		
		add( panelNorte, BorderLayout.NORTH );
		
		*/
		
		panelCentral = new JPanel();
		panelCentral.setLayout( new GridLayout(7, 1) );
		panelCentral.setBackground( Color.GRAY );
		
		
		tituloListaPangramas = new JLabel("\"Algunos pangramas para practicar\"");
		tituloListaPangramas.setFont( new Font("SANS_SERIF", Font.BOLD, 20) );
		
		
		arregloPangramasString = new String[16];
		arregloPangramasString[0] = "El veloz murciélago hindú comía feliz cardillo y kiwi";
		arregloPangramasString[1] = "La cigüeña tocaba el saxofón detrás del palenque de paja";
		arregloPangramasString[2] = "Aquel inexpresivo niño trabajaba mezclando whisky en garrafa";
		arregloPangramasString[3] = "Aquel biografo se zampo un extraño sandwich de vodka y ajo";
		arregloPangramasString[4] = "El alambique de la granja extremeña vaporiza whisky de chufa";
		arregloPangramasString[5] = "El extraño whisky quemo como fuego la boca del joven Lopez";
		arregloPangramasString[6] = "Jovencillo emponzoñado de whisky: ¡qué figurota exhibe!";
		arregloPangramasString[7] = "Queda gazpacho, fibra, látex, jamón, kiwi y viñas";
		arregloPangramasString[8] = "Chaikovsky bajeó exitos del pequeño Wolfgang Mozart";
		arregloPangramasString[9] = "La vieja cigüeña fóbica quiso empezar hoy un éxodo a Kuwait";
		arregloPangramasString[10] = "El mejor fue Karl von Weber, quien ya gozó del éxito por años en China";
		arregloPangramasString[11] = "Benjamín pidió una bebida de kiwi y fresa. Noé, sin vergüenza, la más exquisita champaña del menú";
		arregloPangramasString[12] = "José compró en Perú una vieja zampoña. Excusándose, Sofía tiró su whisky al desagüe de la banqueta";
		arregloPangramasString[13] = "El cadáver de Wamba, rey godo de España, fue exhumado y trasladado en una caja de zinc que pesó un kilo";
		arregloPangramasString[14] = "El baño de wolframio de un equipo de rayos X es capaz de generar unas horas de kilovoltaje";
		arregloPangramasString[15] = "Hoy bajó su valor la wulfenita, extraño molibdato que se cotiza por kilogramo";

		
		pangramas = new JLabel[6];
		pangramas[0] = new JLabel ("1.- "+arregloPangramasString[0]);
		pangramas[1] = new JLabel ("2.- "+arregloPangramasString[1]);
		pangramas[2] = new JLabel ("3.- "+arregloPangramasString[2]);
		pangramas[3] = new JLabel ("4.- "+arregloPangramasString[3]);
		pangramas[4] = new JLabel ("5.- "+arregloPangramasString[4]);
		pangramas[5] = new JLabel ("6.- "+arregloPangramasString[5]);
		
		
		
		panelCentral.add(tituloListaPangramas);
		
		for ( int i = 0; i < pangramas.length; i++)
			panelCentral.add(pangramas[i]);
		
		add( panelCentral, BorderLayout.CENTER );
		
		//creamos objeto de la clase JuegoPangramas
		JuegoPangramas juego = new JuegoPangramas();
		juego.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
		juego.setSize(1100, 120);
		juego.setResizable(false);
		juego.setLocation(180, 50);
		juego.setAlwaysOnTop(true);
		
		
		botonJugarJuego = new JButton ("Click aquí para hacer ejercicios");
		botonJugarJuego.setBackground(Color.DARK_GRAY);
		botonJugarJuego.setForeground(Color.WHITE);
		botonJugarJuego.addActionListener( 
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						 JOptionPane.showMessageDialog(null, "A Continuación se te mostraran varios pangramas,"
						 		+ "\ntrata de escribirlos sin cometer errores.\n\n"
						 		+ "Se te contarán los errores y los aciertos que tengas! \n\n"
						 		+ "Nota : Al terminar cada pangrama sólo tienes que escribir la primera\n"
						 		+ "letra del siguiente para seguir jugando.\n"
						 		+ "Suerte!!");
						//TutorMeca.obtenerAreaTexto().setText(null);//borra el texto
						TutorMeca.borrarPantalla(true);
						ejecutaJuego = true;
						
						juego.setVisible(true);
						setVisible(false);//oculta la ventana que muestra los pangramas
						TutorMeca.establecerTextoAreaPangramas(arregloPangramasString[0]);
						JuegoPangramas.establecerTextoAreaPangramas(arregloPangramasString[0]);
					}
					
				});
		
		add( botonJugarJuego , BorderLayout.SOUTH );
		
		
		
	}//fin del constructor
	
	public static boolean ejecutaJuego (){
		return ejecutaJuego;
	}
	
	
	

}//fin de la clase

//otra clase completa e independiente
class JuegoPangramas extends JFrame{
	
	private static JTextArea areaTextoPangramas;
	
	private Font tipoDeLetra;
	
	private static JLabel barraEstado;
	
	private static int contadorCaracteres = 0;
	
		
	private static int contadorPangramas = 0;
	
	
	public JuegoPangramas (){
		
		super ("Escribe este pangrama en la pantalla");
		
			
		tipoDeLetra = new Font( "SANS_SERIF", Font.BOLD, 30 );//crea tipo de letra
		
		areaTextoPangramas = new JTextArea();
		areaTextoPangramas.setBackground( Color.GRAY );
		areaTextoPangramas.setFont(tipoDeLetra);
		areaTextoPangramas.setEditable(false);
		areaTextoPangramas.setText(Pangramas.arregloPangramasString[0]);
		areaTextoPangramas.setSelectedTextColor( Color.YELLOW );
		areaTextoPangramas.setSelectionColor(null);
		
				
		add( areaTextoPangramas, BorderLayout.CENTER );
		
		//if ( TutorMeca.usuarioEscribe() == true )
		
		barraEstado = new JLabel("Errores = 0 Aciertos = 0");
		
		add( barraEstado, BorderLayout.SOUTH );
			
		
	}//fin del constructor
	
	public void minimizarVentana (){
		this.setVisible(false);
	}
	
	public static JLabel obtenerBarraEstado (){
		return barraEstado;
	}
	
	public static JTextArea obtenerAreaTextoPangramas (){
		return areaTextoPangramas;
	}
	
	public static void establecerContadorCaracteres ( int contadorC ){
		contadorCaracteres = contadorC;
	}
	
	public static void establecerTextoAreaPangramas ( String pangrama ){
		areaTextoPangramas.setText(pangrama);
	}
	
	public static void seleccionaTextoMientrasEscribe (){
		
		contadorCaracteres++;
		areaTextoPangramas.setSelectionStart(0);
		areaTextoPangramas.setSelectionEnd(contadorCaracteres);
		
		
		
	}//fin de metodo seleccionaTextoMientrasEscribe
	
	public static void terminoJuego(){
		contadorCaracteres = 0;
		contadorPangramas = 0;
		barraEstado.setText("Errores = 0 Aciertos = 0");
	}
	
	public static void cambiaPangrama (){
		
					
		if ( Pangramas.arregloPangramasString[contadorPangramas].length() == contadorCaracteres && contadorPangramas < Pangramas.arregloPangramasString[contadorPangramas].length()){
			
			contadorPangramas++;
			contadorCaracteres = 0;
			if ( Pangramas.arregloPangramasString[contadorPangramas].length() > 60 && Pangramas.arregloPangramasString[contadorPangramas].length() <= 70  )
				areaTextoPangramas.setFont( new Font( "SANS_SERIF", Font.BOLD, 25 ) );
			else if ( Pangramas.arregloPangramasString[contadorPangramas].length() > 70 && Pangramas.arregloPangramasString[contadorPangramas].length() <= 80  )
				areaTextoPangramas.setFont( new Font( "SANS_SERIF", Font.BOLD, 23 ) );
			else if ( Pangramas.arregloPangramasString[contadorPangramas].length() > 80  )
				areaTextoPangramas.setFont( new Font( "SANS_SERIF", Font.BOLD, 18 ) );
			areaTextoPangramas.setText(Pangramas.arregloPangramasString[contadorPangramas]);
			TutorMeca.establecerTextoAreaPangramas(Pangramas.arregloPangramasString[contadorPangramas]);
			//TutorMeca.obtenerAreaTexto().setText(null);
		}
		
	}
	
		
	
	private class ManejadorVentana implements WindowListener{

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
			/*
			 * hacer que cuando se abra la pantalla del juego 
			 * el focus se valla hacia la araea de texto
			 * y tambien que el sonido se ejecute cada vesque se cometen errores
			 * 
			 */
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			TutorMeca.obtenerAreaTexto().requestFocusInWindow();
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}//fin de la clase JUegoPangramas
