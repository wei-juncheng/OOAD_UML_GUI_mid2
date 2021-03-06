package bgWork.handler;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Define.AreaDefine;
import Listener.CPHActionListener;
import Pack.DragPack;
import Pack.SendText;
import bgWork.InitProcess;
import mod.instance.AssociationLine;
import mod.instance.BasicClass;
import mod.instance.CompositionLine;
import mod.instance.GeneralizationLine;
import mod.instance.GroupContainer;
import mod.instance.UseCase;
import mod.instance.DependencyLine;

public class CanvasPanelHandler extends PanelHandler
{
	Vector <JPanel>	members		= new Vector <>();
	Vector <JPanel>	selectComp	= new Vector <>();
	int				boundShift	= 10;

	public CanvasPanelHandler(JPanel Container, InitProcess process)
	{
		super(Container, process);
		boundDistance = 10;
		initContextPanel();
		Container.add(this.contextPanel);
	}

	@Override
	void initContextPanel()
	{
		JPanel fphContextPanel = core.getFuncPanelHandler().getContectPanel();
		contextPanel = new JPanel();
		contextPanel.setBounds(
				fphContextPanel.getLocation().x
						+ fphContextPanel.getSize().width + boundShift,
				fphContextPanel.getLocation().y, 800, 600);
		contextPanel.setLayout(null);
		contextPanel.setVisible(true);
		contextPanel.setBackground(Color.WHITE);
		contextPanel.setBorder(new LineBorder(Color.BLACK));
		contextPanel.addMouseListener(new CPHActionListener(this));
	}

	@Override
	public void ActionPerformed(MouseEvent e)
	{
		switch (core.getCurrentFuncIndex())
		{
			case 0:
				selectByClick(e);
				break;
			case 1:
			case 2:
			case 3:
				break;
			case 4:
			case 5:
				addObject(core.getCurrentFunc(), e.getPoint());
				break;
			default:
				break;
		}
		repaintComp();
	}

	public void ActionPerformed(DragPack dp)
	{
		switch (core.getCurrentFuncIndex())
		{
			case 0:
				selectByDrag(dp);
				break;
			case 1:
			case 2:
			case 3:
				addLine(core.getCurrentFunc(), dp);
				break;
			case 4:
			case 5:
				break;
			case 6:
				addLine(core.getCurrentFunc(), dp);
			default:
				break;
		}
		repaintComp();
	}

	public void repaintComp()
	{
		for (int i = 0; i < members.size(); i ++)
		{
			members.elementAt(i).repaint();
		}
		contextPanel.updateUI();
	}

	//??????????????????????????????highlight?????????highlight???
	void setHighlightAllType(Object obj, boolean isHighlight)
	{
		switch (core.isFuncComponent(obj))
		{
			case 2:
				((AssociationLine) obj).is_highlight = isHighlight;
				break;
			case 3:
				((CompositionLine) obj).is_highlight = isHighlight;
				break;
			case 4:
				((GeneralizationLine) obj).is_highlight = isHighlight;
				break;
			case 6:
				((DependencyLine) obj).is_highlight = isHighlight;
				break;
			default:
				break;
		}
	}

	public boolean compare_from_and_to_obj(JPanel selected_obj,int selected_port_num,  JPanel check_from_obj, JPanel check_to_obj, int fromSide, int toSide){
		if(  (selected_obj.equals(check_from_obj) && (selected_port_num ==fromSide))   ||     (selected_obj.equals(check_to_obj) && (selected_port_num==toSide))  ){
			return true;
		}
		 return false;
	}

	public void get_highlight_line(JPanel selected_obj, int selected_port_num)
	{
		for (int i = 0; i < contextPanel.getComponents().length; i ++){
			boolean is_selected_obj = false;

			int component_type = core.isLine(contextPanel.getComponent(i));
			switch (component_type)
				{
					case 0: //AssociationLine
						is_selected_obj = compare_from_and_to_obj(selected_obj  , selected_port_num, ((AssociationLine) contextPanel.getComponent(i)).from , ((AssociationLine) contextPanel.getComponent(i)).to, ((AssociationLine) contextPanel.getComponent(i)).fromSide, ((AssociationLine) contextPanel.getComponent(i)).toSide);
						//??????Line?????????????????????Highlight
						if(is_selected_obj){
							System.out.println("????????? "+ contextPanel.getComponent(i));
							((AssociationLine) contextPanel.getComponent(i)).is_highlight = true;
						}
						else{ //?????????highlight??????
							((AssociationLine) contextPanel.getComponent(i)).is_highlight = false;
						}
						break;
					case 1: //CompositionLine
						is_selected_obj = compare_from_and_to_obj(selected_obj  , selected_port_num,  ((CompositionLine) contextPanel.getComponent(i)).from, ((CompositionLine) contextPanel.getComponent(i)).to, ((CompositionLine) contextPanel.getComponent(i)).fromSide, ((CompositionLine) contextPanel.getComponent(i)).toSide);
						//??????Line?????????????????????Highlight
						if(is_selected_obj){
							System.out.println("????????? "+ contextPanel.getComponent(i));
							((CompositionLine) contextPanel.getComponent(i)).is_highlight = true;
						}
						else{ //?????????highlight??????
							((CompositionLine) contextPanel.getComponent(i)).is_highlight = false;
						}
						break;
					case 2: //GeneralizationLine
						is_selected_obj = compare_from_and_to_obj(selected_obj  , selected_port_num,  ((GeneralizationLine) contextPanel.getComponent(i)).from, ((GeneralizationLine) contextPanel.getComponent(i)).to, ((GeneralizationLine) contextPanel.getComponent(i)).fromSide, ((GeneralizationLine) contextPanel.getComponent(i)).toSide);
						//??????Line?????????????????????Highlight
						if(is_selected_obj){
							System.out.println("????????? "+ contextPanel.getComponent(i));
							((GeneralizationLine) contextPanel.getComponent(i)).is_highlight = true;
						}
						else{ //?????????highlight??????
							((GeneralizationLine) contextPanel.getComponent(i)).is_highlight = false;
						}
						break;
					case 6: //DependencyLine
						is_selected_obj = compare_from_and_to_obj(selected_obj  , selected_port_num,  ((DependencyLine) contextPanel.getComponent(i)).from, ((DependencyLine) contextPanel.getComponent(i)).to, ((DependencyLine) contextPanel.getComponent(i)).fromSide, ((DependencyLine) contextPanel.getComponent(i)).toSide);
						//??????Line?????????????????????Highlight
						if(is_selected_obj){
							System.out.println("????????? "+ contextPanel.getComponent(i));
							((DependencyLine) contextPanel.getComponent(i)).is_highlight = true;
						}
						else{ //?????????highlight??????
							((DependencyLine) contextPanel.getComponent(i)).is_highlight = false;
						}
						break;
					default:
						break;
				}


		}

	}

	void selectByClick(MouseEvent e)
	{
		boolean isSelect = false;
		selectComp = new Vector <>();
		System.out.println("X: "+e.getPoint().x + " Y:"+ e.getPoint().y); //????????????????????????
		for (int i = 0; i < members.size(); i ++)
		{
			if (isInside(members.elementAt(i), e.getPoint()) == true
					&& isSelect == false)
			{
				// System.out.println("members.elementAt(i).getLocation(): "+members.elementAt(i).getLocation());
				// System.out.println("e.getPoint(): " + e.getPoint());
				// System.out.println("getArea(): "+ new AreaDefine().getArea(members.elementAt(i).getLocation(), members.elementAt(i).getSize(), e.getPoint()));
				int selected_port_num = new AreaDefine().getArea(members.elementAt(i).getLocation(), members.elementAt(i).getSize(), e.getPoint());
				
				switch (core.isFuncComponent(members.elementAt(i)))
				{
					case 0: //isBasicClass
						((BasicClass) members.elementAt(i)).setSelect(true);
						selectComp.add(members.elementAt(i));
						isSelect = true;

						get_highlight_line(members.elementAt(i), selected_port_num);
						break;
					case 1: //isUseCase
						((UseCase) members.elementAt(i)).setSelect(true);
						selectComp.add(members.elementAt(i));
						isSelect = true;

						get_highlight_line(members.elementAt(i), selected_port_num);
						break;
					case 5: //isGroupContainer
						Point p = e.getPoint();
						p.x -= members.elementAt(i).getLocation().x;
						p.y -= members.elementAt(i).getLocation().y;
						if (groupIsSelect((GroupContainer) members.elementAt(i),
								p))
						{
							((GroupContainer) members.elementAt(i))
									.setSelect(true);
							selectComp.add(members.elementAt(i));
							isSelect = true;
						}
						else
						{
							((GroupContainer) members.elementAt(i))
									.setSelect(false);
						}

						break;
					case 6:
					default:
						break;
				}
			}
			else
			{
				setSelectAllType(members.elementAt(i), false);
			}
		}
		if (isSelect==false) {
			System.out.println("????????????????????????????????????");
			for (int q = 0; q < contextPanel.getComponents().length; q ++){
				setHighlightAllType(contextPanel.getComponent(q), false);
			}
		}
		repaintComp();
	}

	boolean groupIsSelect(GroupContainer container, Point point)
	{
		for (int i = 0; i < container.getComponentCount(); i ++)
		{
			if (core.isGroupContainer(container.getComponent(i)))
			{
				point.x -= container.getComponent(i).getLocation().x;
				point.y -= container.getComponent(i).getLocation().y;
				if (groupIsSelect((GroupContainer) container.getComponent(i),
						point) == true)
				{
					return true;
				}
				else
				{
					point.x += container.getComponent(i).getLocation().x;
					point.y += container.getComponent(i).getLocation().y;
				}
			}
			else if (core.isJPanel(container.getComponent(i)))
			{
				if (isInside((JPanel) container.getComponent(i), point))
				{
					return true;
				}
			}
		}
		return false;
	}

	boolean selectByDrag(DragPack dp)
	{
		if (isInSelect(dp.getFrom()) == true)
		{
			// dragging components
			Dimension shift = new Dimension(dp.getTo().x - dp.getFrom().x,
					dp.getTo().y - dp.getFrom().y);
			for (int i = 0; i < selectComp.size(); i ++)
			{
				JPanel jp = selectComp.elementAt(i);
				jp.setLocation(jp.getLocation().x + shift.width,
						jp.getLocation().y + shift.height);
				if (jp.getLocation().x < 0)
				{
					jp.setLocation(0, jp.getLocation().y);
				}
				if (jp.getLocation().y < 0)
				{
					jp.setLocation(jp.getLocation().x, 0);
				}
			}
			return true;
		}
		if (dp.getFrom().x > dp.getTo().x && dp.getFrom().y > dp.getTo().y)
		{
			// drag right down from to left up
			groupInversSelect(dp);
			return true;
		}
		else if (dp.getFrom().x < dp.getTo().x && dp.getFrom().y < dp.getTo().y)
		{
			// drag from left up to right down
			groupSelect(dp);
			return true;
		}
		return false;
	}

	public void setGroup()
	{
		if (selectComp.size() > 1)
		{
			GroupContainer gContainer = new GroupContainer(core);
			gContainer.setVisible(true);
			Point p1 = new Point(selectComp.elementAt(0).getLocation().x,
					selectComp.elementAt(0).getLocation().y);
			Point p2 = new Point(selectComp.elementAt(0).getLocation().x,
					selectComp.elementAt(0).getLocation().y);
			Point testP;
			for (int i = 0; i < selectComp.size(); i ++)
			{
				testP = selectComp.elementAt(i).getLocation();
				if (p1.x > testP.x)
				{
					p1.x = testP.x;
				}
				if (p1.y > testP.y)
				{
					p1.y = testP.y;
				}
				if (p2.x < testP.x + selectComp.elementAt(i).getSize().width)
				{
					p2.x = testP.x + selectComp.elementAt(i).getSize().width;
				}
				if (p2.y < testP.y + selectComp.elementAt(i).getSize().height)
				{
					p2.y = testP.y + selectComp.elementAt(i).getSize().height;
				}
			}
			p1.x --;
			p1.y --;
			gContainer.setLocation(p1);
			gContainer.setSize(p2.x - p1.x + 2, p2.y - p1.y + 2);
			for (int i = 0; i < selectComp.size(); i ++)
			{
				JPanel temp = selectComp.elementAt(i);
				removeComponent(temp);
				gContainer.add(temp, i);
				temp.setLocation(temp.getLocation().x - p1.x,
						temp.getLocation().y - p1.y);
			}
			addComponent(gContainer);
			selectComp = new Vector <>();
			selectComp.add(gContainer);
			repaintComp();
		}
	}

	public void setUngroup()
	{
		int size = selectComp.size();
		for (int i = 0; i < size; i ++)
		{
			if (core.isGroupContainer(selectComp.elementAt(i)))
			{
				GroupContainer gContainer = (GroupContainer) selectComp
						.elementAt(i);
				Component temp;
				int j = 0;
				while (gContainer.getComponentCount() > 0)
				{
					temp = gContainer.getComponent(0);
					temp.setLocation(
							temp.getLocation().x + gContainer.getLocation().x,
							temp.getLocation().y + gContainer.getLocation().y);
					addComponent((JPanel) temp, j);
					selectComp.add((JPanel) temp);
					gContainer.remove(temp);
					j ++;
				}
				removeComponent(gContainer);
				selectComp.remove(gContainer);
			}
			repaintComp();
		}
	}

	void groupSelect(DragPack dp)
	{
		JPanel jp = new JPanel();
		jp.setLocation(dp.getFrom());
		jp.setSize(Math.abs(dp.getTo().x - dp.getFrom().x),
				Math.abs(dp.getTo().y - dp.getFrom().x));
		selectComp = new Vector <>();
		for (int i = 0; i < members.size(); i ++)
		{
			if (isInside(jp, members.elementAt(i)) == true)
			{
				selectComp.add(members.elementAt(i));
				setSelectAllType(members.elementAt(i), true);
			}
			else
			{
				setSelectAllType(members.elementAt(i), false);
			}
		}
	}

	void groupInversSelect(DragPack dp)
	{
		JPanel jp = new JPanel();
		jp.setLocation(dp.getTo());
		jp.setSize(Math.abs(dp.getTo().x - dp.getFrom().x),
				Math.abs(dp.getTo().y - dp.getFrom().x));
		selectComp = new Vector <>();
		for (int i = 0; i < members.size(); i ++)
		{
			if (isInside(jp, members.elementAt(i)) == false)
			{
				selectComp.add(members.elementAt(i));
				setSelectAllType(members.elementAt(i), true);
			}
			else
			{
				setSelectAllType(members.elementAt(i), false);
			}
		}
	}

	boolean isInSelect(Point point)
	{
		for (int i = 0; i < selectComp.size(); i ++)
		{
			if (isInside(selectComp.elementAt(i), point) == true)
			{
				return true;
			}
		}
		return false;
	}

	void addLine(JPanel funcObj, DragPack dPack)
	{
		for (int i = 0; i < members.size(); i ++)
		{
			//??????????????????????????????????????????
			if (isInside(members.elementAt(i), dPack.getFrom()) == true)
			{
				dPack.setFromObj(members.elementAt(i));
			}
			if (isInside(members.elementAt(i), dPack.getTo()) == true)
			{
				dPack.setToObj(members.elementAt(i));
			}
		}
		//????????????????????????????????????????????????
		//???
		//???????????????????????????????????????????????????????????????
		if (dPack.getFromObj() == dPack.getToObj()
				|| dPack.getFromObj() == contextPanel
				|| dPack.getToObj() == contextPanel)
		{
			return;
		}
		switch (members.size())
		{
			//1??????????????????????????????
			case 0:
			case 1:
				break;
			default:
				System.out.println("addLine core.isLine(funcObj):"+core.isLine(funcObj));
				switch (core.isLine(funcObj))
				{
					case 0:
						((AssociationLine) funcObj).setConnect(dPack);
						break;
					case 1:
						((CompositionLine) funcObj).setConnect(dPack);
						break;
					case 2:
						((GeneralizationLine) funcObj).setConnect(dPack);
						break;
					case 6:
						((DependencyLine) funcObj).setConnect(dPack);
						break;
					default:
						break;
				}
				contextPanel.add(funcObj, 0);
				break;
		}
	}

	void addObject(JPanel funcObj, Point point)
	{
		if (members.size() > 0)
		{
			members.insertElementAt(funcObj, 0);
		}
		else
		{
			members.add(funcObj);
		}
		members.elementAt(0).setLocation(point);
		members.elementAt(0).setVisible(true);
		contextPanel.add(members.elementAt(0), 0);
	}

	public boolean isInside(JPanel container, Point point)
	{
		Point cLocat = container.getLocation();
		Dimension cSize = container.getSize();
		if (point.x >= cLocat.x && point.y >= cLocat.y)
		{
			if (point.x <= cLocat.x + cSize.width
					&& point.y <= cLocat.y + cSize.height)
			{
				return true;
			}
		}
		return false;
	}

	public boolean isInside(JPanel container, JPanel test)
	{
		Point cLocat = container.getLocation();
		Dimension cSize = container.getSize();
		Point tLocat = test.getLocation();
		Dimension tSize = test.getSize();
		if (cLocat.x <= tLocat.x && cLocat.y <= tLocat.y)
		{
			if (cLocat.x + cSize.width >= tLocat.x + tSize.width
					&& cLocat.y + cSize.height >= tLocat.y + tSize.height)
			{
				return true;
			}
		}
		return false;
	}

	public JPanel getSingleSelectJP()
	{
		if (selectComp.size() == 1)
		{
			return selectComp.elementAt(0);
		}
		return null;
	}

	public void setContext(SendText tr)
	{
		System.out.println(tr.getText());
		try
		{
			switch (core.isClass(tr.getDest()))
			{
				case 0:
					((BasicClass) tr.getDest()).setText(tr.getText());
					break;
				case 1:
					((UseCase) tr.getDest()).setText(tr.getText());
					break;
				default:
					break;
			}
		}
		catch (Exception e)
		{
			System.err.println("CPH error");
		}
	}

	void addComponent(JPanel comp)
	{
		contextPanel.add(comp, 0);
		members.insertElementAt(comp, 0);
	}

	void addComponent(JPanel comp, int index)
	{
		contextPanel.add(comp, index);
		members.insertElementAt(comp, index);
	}

	public void removeComponent(JPanel comp)
	{
		contextPanel.remove(comp);
		members.remove(comp);
	}

	void setSelectAllType(Object obj, boolean isSelect)
	{
		switch (core.isFuncComponent(obj))
		{
			case 0:
				((BasicClass) obj).setSelect(isSelect);
				break;
			case 1:
				((UseCase) obj).setSelect(isSelect);
				break;
			case 2:
				((AssociationLine) obj).setSelect(isSelect);
				break;
			case 3:
				((CompositionLine) obj).setSelect(isSelect);
				break;
			case 4:
				((GeneralizationLine) obj).setSelect(isSelect);
				break;
			case 5:
				((GroupContainer) obj).setSelect(isSelect);
				break;
			default:
				break;
		}
	}

	public Point getAbsLocation(Container panel)
	{
		Point location = panel.getLocation();
		while (panel.getParent() != contextPanel)
		{
			panel = panel.getParent();
			location.x += panel.getLocation().x;
			location.y += panel.getLocation().y;
		}
		return location;
	}
}
