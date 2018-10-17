package be.nils.familyfinder.editor;

import be.nils.familyfinder.model.Family;
import be.nils.familyfinder.repository.FamilyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route("home")
public class MainView extends VerticalLayout {

    private final FamilyRepository familyRepository;

    private final Grid<Family> grid;
    private final TextField filter;
    private final Button addNewBtn;

    public MainView(FamilyRepository familyRepository, FamilyEditor editor) {
        this.familyRepository = familyRepository;
        this.grid = new Grid<>(Family.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New family", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "additionalInfo");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by additional info");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listFamilies(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editFamily(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editFamily(new Family()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listFamilies(filter.getValue());
        });

        // Initialize listing
        listFamilies(null);
    }

    void listFamilies(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(familyRepository.findAll());
        } else {
            grid.setItems(familyRepository.findByAdditionalInfoStartsWithIgnoreCase(filterText));
        }
    }
}
