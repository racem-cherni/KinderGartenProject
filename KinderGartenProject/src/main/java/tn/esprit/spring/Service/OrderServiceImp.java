package tn.esprit.spring.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Order;
import tn.esprit.spring.entities.OrderState;
import tn.esprit.spring.entities.Panier;
import tn.esprit.spring.entities.PanierProduct;
import tn.esprit.spring.entities.PanierSession;
import tn.esprit.spring.entities.PointsHistory;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.PanierSessionRepository;
import tn.esprit.spring.repository.OfferRepository;
import tn.esprit.spring.repository.PanierRepository;
import tn.esprit.spring.repository.PointsHistoryRepository;
import tn.esprit.spring.repository.OrderRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class OrderServiceImp implements OrderService {

	@Autowired
	private OrderRepository OrderRepository;

	@Autowired
	private OfferRepository OfferRepository;

	@Autowired
	private PanierRepository PanierRepository;

	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private PointsHistoryRepository PointsHistoryRepository;
	
	@Autowired
	private PanierSessionRepository PanierSessionRepository;
	
	@Autowired
	private PanierProductService PanierProductService;

	@Override
	public Order addOrder(Order p, int panierid, Long userid) {

		double totalprice = 0;

		p.setState(OrderState.WAITING);

		Panier pa = PanierRepository.findById(panierid).orElse(null);

		for (PanierProduct pp : pa.getOrderedoffers()) {

			totalprice += pp.getQty() * pp.getOffer().getPrice();

		}
		
		p.setTotal_price(totalprice);

		p.setUser(UserRepository.findById(userid).orElse(null));

		p.setPanier(pa);
		p.setOrder_date(new Date());

		UserApp user = UserRepository.findById(userid).orElse(null);
		PanierSession panier_session = PanierSessionRepository.getPanierSessionByUser(userid);
		
		if (p.getPointspent()>0)
			PointsHistoryRepository.save(new PointsHistory(p.getUser(), (int) p.getPointspent() * (-1)));

		PanierSessionRepository.deleteById(panier_session.getId());
		return OrderRepository.save(p);
	}

	@Override
	public void deleteOrder(int id) {

		OrderRepository.deleteById(id);
	}

	@Override
	public List<Order> retrieveAllOrders() {

		List<Order> products = (List<Order>) OrderRepository.findAll();
		return products;
	}

	@Override
	public Order updateOrder(Order u) {

		return OrderRepository.save(u);
	}

	@Override
	public Order retrieveOrder(int id) {
		Order p = OrderRepository.findById((id)).orElse(null);
		return p;
	}

	@Override
	public int getTotalOrders() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Order cancelOrder(int id) {

		Order o = OrderRepository.findById(id).orElse(null);

		if (o.getState().equals(OrderState.WAITING) || o.getState().equals(OrderState.CONFIRMED)) {

			if (o.getPointspent() > 0) {
				PointsHistoryRepository.save(new PointsHistory(o.getUser(), (int) o.getPointspent()));
			}

			o.setState(OrderState.CANCELED);
			PanierProductService.cancelAllProductsByPanier(o.getPanier().getId());
			return OrderRepository.save(o);

		}

		throw new RuntimeException("You can't CANCEL this !");

	}

	@Override
	public Order refuseOrder(int id) {

		Order o = OrderRepository.findById(id).orElse(null);

		if (o.getState().equals(OrderState.WAITING) || o.getState().equals(OrderState.CONFIRMED)) {
			
			if (o.getPointspent() > 0) {
				PointsHistoryRepository.save(new PointsHistory(o.getUser(), (int) o.getPointspent()));
			}

			o.setState(OrderState.REFUSED);
			PanierProductService.cancelAllProductsByPanier(o.getPanier().getId());
			return OrderRepository.save(o);

		}

		throw new RuntimeException("You can't REFUSE this !");

	}

	@Override
	public Order confirmOrder(int id) {

		Order o = this.retrieveOrder(id);

		if (o.getState().equals(OrderState.WAITING)) {

			o.setState(OrderState.CONFIRMED);
			return OrderRepository.save(o);

		}

		throw new RuntimeException("You can't CONFIRM this !");
	}

	@Override
	public Order dispatchOrder(int id) {

		Order o = this.retrieveOrder(id);
		Offer of = new Offer();
		int count = 0;

		if (!(o.getState().equals(OrderState.CONFIRMED)))
			throw new RuntimeException("You must CONFIRM the order before DISPATCHING it !");

		for (PanierProduct pp : o.getPanier().getOrderedoffers()) {

			of = pp.getOffer();

			if (of.getQty() > pp.getQty()) {

				count++;
				of.setQty(of.getQty() - pp.getQty());

				OfferRepository.save(of);

			}

		}

		if (count == o.getPanier().getOrderedoffers().size()) {

			o.setState(OrderState.DISPATCHED);
			
			for (PanierProduct p : o.getPanier().getOrderedoffers()) {

				if (p.getRefuser() != null) {
					PointsHistory ph = new PointsHistory(p.getRefuser(),
							(int) Math.ceil(p.getOffer().getPrice() * 0.05 * 1000));
					PointsHistoryRepository.save(ph);
				}

			}

		}

		/*
		 * if (op.getQty() > o.getQty()) {
		 * 
		 * o.setState(OrderState.DISPATCHED); /op.setQty(op.getQty() -
		 * o.getQty()); OfferRepository.save(op); o = OrderRepository.save(o);
		 */
		return OrderRepository.save(o);
		// }

		// o.setState(OrderState.WAITING);
		// OrderRepository.save(o);
		// throw new RuntimeException("Product out of stock !, ORDER state set
		// back to WAITING");
	}

}