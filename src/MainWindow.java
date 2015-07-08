import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JSlider;

import java.lang.Exception;

/** @brief A window to configure the new Map.
 ** @details The window allows for selection of the number of players, which expansions to use, and custom sizing.
 ** @field gbc The GridBagConstraints used for the placement of components within the window's GridBagLayout.
 **/
public class MainWindow extends JFrame
{
	GridBagConstraints gbc;

	/** @brief Default constructor for a MainWindow.
	 ** @details Draws all of the components for configuring the new Map.
	 **/
	public MainWindow()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Catan Map Generator");
		this.setResizable(false);

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		final JCheckBox sizeChk = new JCheckBox();
		final JPanel sizePanel = new JPanel();
			final JTextField heightTxt = new JTextField();
			final JTextField widthTxt = new JTextField();
		final JCheckBox desertChk = new JCheckBox();
		final JPanel desertPanel = new JPanel();
			final JSlider desertSld = new JSlider(1, 1, 1);
			final JLabel desertLbl = new JLabel();
		final JCheckBox playerChk = new JCheckBox();
		final JCheckBox oceanChk = new JCheckBox();
		final JCheckBox explorersChk = new JCheckBox();
		final JPanel explorerPanel = new JPanel();
			final JCheckBox sunChk = new JCheckBox();
			final JCheckBox moonChk = new JCheckBox();
		final JButton generateBtn = new JButton();


		/////////////////////////////////////////////
		// Size Panel							   //
		/////////////////////////////////////////////
		sizeChk.setText("Custom Size");
		changeGBC(0, 0, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(sizeChk, gbc);
		
		sizePanel.setVisible(false);
		sizePanel.setLayout(new GridBagLayout());
		changeGBC(0, 1, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(sizePanel, gbc);
		sizeChk.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					setComponentVisibility(sizePanel, true);
				}
				else
				{
					setComponentVisibility(sizePanel, false);
				}
			}
		});

		/////////////////////////////////////////////
		// HeightTxt 							   //
		/////////////////////////////////////////////
		changeGBC(0, 0, 1, 1, GridBagConstraints.WEST, 0, 0);
		sizePanel.add(new JLabel("Height: "), gbc);
		
		changeGBC(1, 0, 1, 1, GridBagConstraints.NORTHWEST, 1, 1);
		sizePanel.add(new JPanel(), gbc);

		heightTxt.setPreferredSize(new Dimension(50, 20));
		changeGBC(2, 0, 1, 1, GridBagConstraints.EAST, 0, 0);
		sizePanel.add(heightTxt, gbc);

		/////////////////////////////////////////////
		// WidthTxt 							   //
		/////////////////////////////////////////////
		changeGBC(0, 1, 1, 1, GridBagConstraints.WEST, 0, 0);
		sizePanel.add(new JLabel("Width: "), gbc);
		
		changeGBC(1, 1, 1, 1, GridBagConstraints.NORTHWEST, 1, 1);
		sizePanel.add(new JPanel(), gbc);

		widthTxt.setPreferredSize(new Dimension(50, 20));
		changeGBC(2, 1, 1, 1, GridBagConstraints.EAST, 0, 0);
		sizePanel.add(widthTxt, gbc);

		/////////////////////////////////////////////
		// Desert Chk							   //
		/////////////////////////////////////////////
		desertChk.setText("Desert");
		changeGBC(0, 2, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(desertChk, gbc);

		desertPanel.setVisible(false);
		desertPanel.setLayout(new GridBagLayout());
		changeGBC(0, 3, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(desertPanel, gbc);

		updateDesertSld(desertSld, playerChk.isSelected(), oceanChk.isSelected());

		desertChk.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					setComponentVisibility(desertPanel, true);
				}
				else
				{
					setComponentVisibility(desertPanel, false);
				}
			}
		});

		/////////////////////////////////////////////
		// DesertSld 							   //
		/////////////////////////////////////////////
		changeGBC(0, 0, 1, 1, GridBagConstraints.WEST, 1, 0);
		desertPanel.add(desertSld, gbc);
		desertSld.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				desertLbl.setText("" + desertSld.getValue());
			}
		});

		changeGBC(1, 0, 1, 1, GridBagConstraints.WEST, 0, 0);
		desertLbl.setText("" + desertSld.getValue());
		desertPanel.add(desertLbl, gbc);

		/////////////////////////////////////////////
		// Player Panel							   //
		/////////////////////////////////////////////
		playerChk.setText("6 Players");
		changeGBC(0, 4, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(playerChk, gbc);
		playerChk.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				updateDesertSld(desertSld, playerChk.isSelected(), oceanChk.isSelected());
			}
		});

		/////////////////////////////////////////////
		// Seafarers Chk						   //
		/////////////////////////////////////////////
		changeGBC(0, 5, 3, 1, GridBagConstraints.WEST, 1, 0);
		oceanChk.setText("Seafarers");
		add(oceanChk, gbc);
		oceanChk.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				updateDesertSld(desertSld, playerChk.isSelected(), oceanChk.isSelected());
			}
		});

		/////////////////////////////////////////////
		// Explorers Panel							   //
		/////////////////////////////////////////////
		explorersChk.setText("Explorers & Pirates");
		changeGBC(0, 6, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(explorersChk, gbc);
		
		explorerPanel.setVisible(false);
		explorerPanel.setLayout(new GridBagLayout());
		changeGBC(0, 7, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(explorerPanel, gbc);

		explorersChk.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					setComponentVisibility(explorerPanel, true);
				}
				else
				{
					setComponentVisibility(explorerPanel, false);
				}
			}
		});

		/////////////////////////////////////////////
		// Suns		 							   //
		/////////////////////////////////////////////
		changeGBC(0, 0, 1, 1, GridBagConstraints.WEST, 1, 0);
		sunChk.setText("Suns");
		sunChk.setSelected(true);
		explorerPanel.add(sunChk, gbc);

		/////////////////////////////////////////////
		// Moons	 							   //
		/////////////////////////////////////////////
		changeGBC(0, 1, 1, 1, GridBagConstraints.WEST, 1, 0);
		moonChk.setText("Moons");
		moonChk.setSelected(true);
		explorerPanel.add(moonChk, gbc);

		/////////////////////////////////////////////
		// Spacing	 							   //
		/////////////////////////////////////////////
		changeGBC(0, 8, 3, 1, GridBagConstraints.NORTHWEST, 1, 1);
		add(new JPanel(), gbc);

		/////////////////////////////////////////////
		// GenerateBtn 							   //
		/////////////////////////////////////////////
		changeGBC(1, 9, 1, 1, GridBagConstraints.NORTHWEST, 1, 0);
		generateBtn.setText("Generate");
		generateBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/////////////////////////////////////
				// Set up tile counts			   //
				/////////////////////////////////////
				int outHeight = 5;
				int outWidth = 5;
				int maxTileCount = 19;
				if(!desertChk.isSelected())
					{
						maxTileCount = 18;
					}
				int numberOfOceans = 0;

				String errors = "";

				//Vanilla
				if(playerChk.isSelected())
				{
					outHeight = 7;
					outWidth  = 6;
					maxTileCount = 30;
					if(!desertChk.isSelected())
					{
						maxTileCount = 28;
					}
				}

				//Seafarers
				if(oceanChk.isSelected())
				{
					if(playerChk.isSelected())
					{
						outHeight = 7;
						outWidth = 10;
						maxTileCount = 80;
						numberOfOceans = 18;
						if(!desertChk.isSelected())
						{
							maxTileCount = 75;
						}
					}
					else
					{
						outHeight = 7;
						outWidth = 8;
						maxTileCount = 59;
						if(!desertChk.isSelected())
						{
							maxTileCount = 56;
						}
						numberOfOceans = 16;
					}
				}

				//Custom size
				SpreadEvenMap outMap = null;
				if(sizeChk.isSelected())
				{
					try
					{
						outHeight = Integer.parseInt(heightTxt.getText());
					}
					catch(Exception x)
					{
						errors += "Invalid height.\n";
					}
					try
					{
						outWidth = Integer.parseInt(widthTxt.getText());
					}
					catch(Exception x)
					{
						errors += "Invalid width.\n";
					}
					if(outHeight%2 == 0)
					{
						errors += "Height cannot be even.\n";
					}
					if(outWidth - (outHeight - 1)/2 < 1)
					{
						errors += "Width too small for height.";
					}

					if(errors == "")
					{
						outMap = new SpreadEvenMap(outHeight, outWidth);
						if(outMap.getTileCount() > maxTileCount)
						{
							errors = "Not enough tiles available for given size.";
						}
						numberOfOceans = outMap.getTileCount() / 3;
					}
				}
				else
				{
					if(errors == "")
					{
						outMap = new SpreadEvenMap(outHeight, outWidth);
						if(outMap.getTileCount() > maxTileCount)
						{
							errors = "Not enough tiles available for given options.";
						}
					}
				}

				/////////////////////////////////////
				// Generate Map 				   //
				/////////////////////////////////////
				if(errors == "")
				{
					if(outMap == null)
					{
						outMap = new SpreadEvenMap(outHeight, outWidth);
					}
					if(oceanChk.isSelected())
					{
						outMap.splitLand(numberOfOceans);
					}
					MapNode first = new MapNode(outMap, 0, outMap.getTileCount(), outMap.getTypes());
					
					if(desertChk.isSelected())
					{
						int i;
						for(i = 0; i < desertSld.getValue(); i++)
						{
							outMap.placeDesert();
						}
					}

					TileWindow tw = new TileWindow(outMap);
				}
				else
				{
					JOptionPane.showMessageDialog(null, errors, "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		add(generateBtn, gbc);

		this.pack();
		this.setVisible(true);
	}

	/** @brief Updates the maximum for desertSld.
	 ** @param inSlider The JSlider desertSld.
	 ** @param inPlayerFlag The return of playerChk.isSelected().
	 ** @param inOceanFlag The return of oceanChk.isSelected().
	 ** @return void Returns nothing.
	 **/
	void updateDesertSld(JSlider inSlider, boolean inPlayerFlag, boolean inOceanFlag)
	{
		//Count max deserts.
		int desMax = 1;
		if(inPlayerFlag)
		{
			desMax++;
		}
		if(inOceanFlag)
		{
			desMax += 2;
			if(inPlayerFlag)
			{
				desMax++;
			}
		}

		inSlider.setMaximum(desMax);
	}

	/** @brief Functions to change values of the GridBagConstraints object.
	 ** @param inX New X position in the GridBagLayout.
	 ** @param inY New Y position in the GridBagLayout.
	 ** @param inWidth Amount of cells wide to be in the GridBagLayout.
	 ** @param inHeight Amount of cells tall to be in the GridBagLayout.
	 ** @param inAnchor Edge to anchor to in the GridBagLayout.
	 ** @param inWeightX The weighting used to determine how much of the space to claim when the window is resized, in the X-axis.
	 ** @param inWeightY The weighting used to determine how much of the space to claim when the window is resized, in the Y-axis.
	 ** @return void Returns nothing.
	 **/
	void changeGBC(int inX, int inY, int inWidth, int inHeight, int inAnchor, double inWeightX, double inWeightY) 
	{
		gbc.gridx = inX;
		gbc.gridy = inY;
		gbc.gridwidth = inWidth;
		gbc.gridheight = inHeight;
		gbc.anchor = inAnchor;
		gbc.weightx = inWeightX;
		gbc.weighty = inWeightY;
	}

	/** @brief Shows or hides the input JComponent.
	 ** @details This is used by the Listeners when further components are required.
	 ** @param inComponent The JComponent to show/hide.
	 ** @param inBool The new visibility state of the JComponent.
	 ** @return void Returns nothing.
	 **/
	void setComponentVisibility(JComponent inComponent, boolean inBool)
	{
		inComponent.setVisible(inBool);
		this.pack();
	}
}