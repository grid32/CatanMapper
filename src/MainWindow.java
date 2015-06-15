import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

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

		/////////////////////////////////////////////
		// Size Panel						   //
		/////////////////////////////////////////////
		final JCheckBox sizeHeadChk = new JCheckBox("Custom Size");
		changeGBC(0, 0, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(sizeHeadChk, gbc);
		
		final JPanel sizePanel = new JPanel();
		sizePanel.setVisible(false);
		sizePanel.setLayout(new GridBagLayout());
		changeGBC(0, 1, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(sizePanel, gbc);

		sizeHeadChk.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					setPanelVisibility(sizePanel, true);
				}
				else
				{
					setPanelVisibility(sizePanel, false);
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

		final JTextField heightTxt = new JTextField();
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

		final JTextField widthTxt = new JTextField();
		widthTxt.setPreferredSize(new Dimension(50, 20));
		changeGBC(2, 1, 1, 1, GridBagConstraints.EAST, 0, 0);
		sizePanel.add(widthTxt, gbc);

		/////////////////////////////////////////////
		// Desert Chk							   //
		/////////////////////////////////////////////
		final JCheckBox desertChk = new JCheckBox("Desert");
		changeGBC(0, 2, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(desertChk, gbc);

		/////////////////////////////////////////////
		// Player Panel							   //
		/////////////////////////////////////////////
		final JCheckBox playerHeadChk = new JCheckBox("6 Players");
		changeGBC(0, 3, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(playerHeadChk, gbc);

		/////////////////////////////////////////////
		// Seafarers Chk						   //
		/////////////////////////////////////////////
		changeGBC(0, 4, 3, 1, GridBagConstraints.WEST, 1, 0);
		final JCheckBox oceanChk = new JCheckBox("Seafarers");
		add(oceanChk, gbc);

		/////////////////////////////////////////////
		// Explorers Panel							   //
		/////////////////////////////////////////////
		final JCheckBox explorersHeadChk = new JCheckBox("Explorers & Pirates");
		changeGBC(0, 5, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(explorersHeadChk, gbc);
		
		final JPanel explorerPanel = new JPanel();
		explorerPanel.setVisible(false);
		explorerPanel.setLayout(new GridBagLayout());
		changeGBC(0, 6, 3, 1, GridBagConstraints.NORTHWEST, 1, 0);
		add(explorerPanel, gbc);

		explorersHeadChk.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					setPanelVisibility(explorerPanel, true);
				}
				else
				{
					setPanelVisibility(explorerPanel, false);
				}
			}
		});

		/////////////////////////////////////////////
		// Suns		 							   //
		/////////////////////////////////////////////
		changeGBC(0, 0, 1, 1, GridBagConstraints.WEST, 1, 0);
		final JCheckBox sunChk = new JCheckBox("Suns");
		sunChk.setSelected(true);
		explorerPanel.add(sunChk, gbc);

		/////////////////////////////////////////////
		// Moons	 							   //
		/////////////////////////////////////////////
		changeGBC(0, 1, 1, 1, GridBagConstraints.WEST, 1, 0);
		final JCheckBox moonChk = new JCheckBox("Moons");
		moonChk.setSelected(true);
		explorerPanel.add(moonChk, gbc);

		/////////////////////////////////////////////
		// Spacing	 							   //
		/////////////////////////////////////////////
		changeGBC(0, 7, 3, 1, GridBagConstraints.NORTHWEST, 1, 1);
		add(new JPanel(), gbc);

		/////////////////////////////////////////////
		// GenerateBtn 							   //
		/////////////////////////////////////////////
		changeGBC(1, 8, 1, 1, GridBagConstraints.NORTHWEST, 1, 0);
		JButton generateBtn = new JButton("Generate");
		generateBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int outHeight = 5;
				int outWidth = 5;

				String errors = "";

				if(playerHeadChk.isSelected())
				{
					outHeight = 5;
					outWidth = 7;
				}

				if(sizeHeadChk.isSelected())
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
				}

				if(errors == "")
				{
					if(oceanChk.isSelected())
					{
						if(!sizeHeadChk.isSelected())
						{
							if(playerHeadChk.isSelected())
							{
								outHeight = 7;
								outWidth = 9;
							}
							else
							{
								outHeight = 5;
								outWidth = 7;
							}
						}
					}
					SpreadEvenMap outMap = new SpreadEvenMap(outHeight, outWidth);
					if(oceanChk.isSelected())
					{
						int numberOfOceans;
						if(!sizeHeadChk.isSelected())
						{
							if(playerHeadChk.isSelected())
							{
								numberOfOceans = outMap.getTileCount() - 29;
							}
							else
							{
								numberOfOceans = outMap.getTileCount() - 19;
							}
						}
						else
						{
							numberOfOceans = 0;
						}
						outMap.splitLand(numberOfOceans);
					}
					MapNode first = new MapNode(outMap, 0, outMap.getTileCount(), outMap.getTypes());
					
					if(desertChk.isSelected())
					{
						outMap.placeDesert();
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

	/** @brief Shows or hides the input JPanel.
	 ** @details This is used by the Listeners when further components are required.
	 ** @param inPanel The JPanel to show/hide.
	 ** @param inBool The new visibility state of the JPanel.
	 ** @return void Returns nothing.
	 **/
	void setPanelVisibility(JPanel inPanel, boolean inBool)
	{
		inPanel.setVisible(inBool);
		this.pack();
	}
}