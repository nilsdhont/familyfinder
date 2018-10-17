package be.nils.familyfinder.editor;

import be.nils.familyfinder.model.Family;
import be.nils.familyfinder.repository.FamilyRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class FamilyEditor extends VerticalLayout implements KeyNotifier {

    private final FamilyRepository repository;

    /**
     * The currently edited family
     */
    private Family family;

    /* Fields to edit properties in Customer entity */
    TextField additionalInfo = new TextField("Additional info");

    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Family> binder = new Binder<>(Family.class);
    private ChangeHandler changeHandler;

    @Autowired
    public FamilyEditor(FamilyRepository repository) {
        this.repository = repository;
        add(additionalInfo, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editFamily(family));
        setVisible(false);
    }

    void delete() {
        repository.delete(family);
        changeHandler.onChange();
    }

    void save() {
        repository.save(family);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editFamily(Family family) {
        if (family == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = family.getId() != 0;
        if (persisted) {
            // Find fresh entity for editing
            this.family = repository.findById(family.getId()).get();
        } else {
            this.family = family;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(family);

        setVisible(true);

        // Focus first name initially
        additionalInfo.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }
}
