package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.CategoryRepository;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.model.Category;
import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.Repository.SupportAgentRepository;
import com.spring_boot.CSTS.Repository.TeamRepository;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportAgentService {
    @Autowired
    private SupportAgentRepository agentRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TeamRepository teamRepository;

    public SupportAgent createAgent(Long categoryId,Long teamId, SupportAgent agent) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("category not found"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        agent.setCategory(category);

        agent.setTeam(team);
        return agentRepository.save(agent);
    }
    public List<SupportAgent> getAgents(){
        return agentRepository.findAll();
    }


    public Optional<SupportAgent> getAgentById  (String agentusername){
        return agentRepository.findByUsername(agentusername);
    }
    public Ticket updateTicketStatus(Long ticketId, Long agentId, Ticket.Status newStatus){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));


        if (!ticket.getAssignedTo().getId().equals(agentId)) {
            throw new RuntimeException("Unauthorized: This ticket is not assigned to the agent");
        }
        ticket.setStatus(newStatus);
      Ticket updateTicket =ticketRepository.save(ticket);
      return updateTicket;
    }
}
